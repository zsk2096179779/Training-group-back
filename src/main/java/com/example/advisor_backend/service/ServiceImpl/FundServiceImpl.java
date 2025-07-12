// src/main/java/com/example/advisor_backend/service/impl/FundServiceImpl.java
package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.exception.BusinessException;
import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.model.entity.Fund;
import com.example.advisor_backend.repository.*;
import com.example.advisor_backend.service.FundService;
import com.example.advisor_backend.service.FundStatsService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FundServiceImpl implements FundService {

    private final FundRepository              fundRepo;
    private final LabelRepository             labelRepo;
    private final NavHistoryRepository        navHistoryRepo;
    private final FundStatsService            fundStatsService;
    private final FundHoldingRepository holdingRepo;
    private final AttributionRepository       attributionRepo;
    private final AnnouncementRepository      announcementRepo;

    public FundServiceImpl(FundRepository fundRepo,
                           LabelRepository labelRepo,
                           NavHistoryRepository navHistoryRepo,
                           FundStatsService fundStatsService,
                           FundHoldingRepository holdingRepo,
                           AttributionRepository attributionRepo,
                           AnnouncementRepository announcementRepo) {
        this.fundRepo           = fundRepo;
        this.labelRepo          = labelRepo;
        this.navHistoryRepo     = navHistoryRepo;
        this.fundStatsService   = fundStatsService;
        this.holdingRepo        = holdingRepo;
        this.attributionRepo    = attributionRepo;
        this.announcementRepo   = announcementRepo;
    }

    @Override
    public PageResult<FundResponse> listFunds(FundFilterRequest req) {
        Pageable pg = PageRequest.of(req.getPage() - 1, req.getSize(), Sort.by("code"));
        Specification<Fund> spec = (root, q, cb) -> {
            Predicate p = cb.conjunction();

            if (StringUtils.hasText(req.getCode())) {
                String like = "%" + req.getCode().trim() + "%";
                p = cb.and(p, cb.or(
                        cb.like(root.get("code"), like),
                        cb.like(root.get("name"), like)
                ));
            }
            if (StringUtils.hasText(req.getCompanyName())) {
                p = cb.and(p, cb.like(root.get("companyName"),
                        "%" + req.getCompanyName().trim() + "%"));
            }
            if (StringUtils.hasText(req.getManagerName())) {
                p = cb.and(p, cb.like(root.get("managerName"),
                        "%" + req.getManagerName().trim() + "%"));
            }
            if (req.getLabels() != null && !req.getLabels().isEmpty()) {
                Join<Fund, ?> join = root.join("labels", JoinType.INNER);
                p = cb.and(p, join.get("id").in(req.getLabels()));
                q.distinct(true);
            }
            return p;
        };

        Page<Fund> page = fundRepo.findAll(spec, pg);
        List<FundResponse> items = page.getContent().stream().map(f -> {
            FundResponse fr = new FundResponse();
            fr.setCode(f.getCode());
            fr.setName(f.getName());
            fr.setNav(f.getNav());
            fr.setRisk(f.getRisk());
            fr.setCompanyName(f.getCompanyName());
            fr.setLabels(f.getLabels().stream()
                    .map(l -> new LabelResponse(l.getId(), l.getName()))
                    .collect(Collectors.toList()));
            return fr;
        }).collect(Collectors.toList());

        return new PageResult<>(items, page.getTotalElements());
    }

    @Override
    public List<LabelResponse> listAllLabels() {
        return labelRepo.findAll().stream()
                .map(l -> new LabelResponse(l.getId(), l.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public FundProfileResponse getProfile(String code) {
        Fund f = fundRepo.findByCode(code)
                .orElseThrow(() -> new BusinessException("基金不存在"));

        FundProfileResponse p = new FundProfileResponse();
        p.setCode(f.getCode());
        p.setName(f.getName());
        p.setCompanyName(f.getCompanyName());
        p.setManagerName(f.getManagerName());
        if (f.getInceptionDate() != null) {
            // 假设 f.getInceptionDate() 的类型是 java.time.LocalDateTime
            p.setInceptionDate(f.getInceptionDate().toLocalDate());
        } else {
            p.setInceptionDate(null);
        }
        p.setNav(f.getNav());
        p.setRisk(f.getRisk());

        // 历史净值
        p.setNavHistory(navHistoryRepo.findByFund_Code(code).stream().map(n -> {
            FundProfileResponse.TimeValue tv = new FundProfileResponse.TimeValue();
            tv.setDate(n.getDate().toString());
            tv.setValue(n.getNav());
            return tv;
        }).collect(Collectors.toList()));

        // 业绩指标（用 DTO 内部的 Metric）
        p.setMetrics(List.of(
                new FundProfileResponse.Metric("年化收益",  fundStatsService.getAnnualizedReturn(code)),
                new FundProfileResponse.Metric("夏普率",    fundStatsService.getSharpeRatio(code)),
                new FundProfileResponse.Metric("最大回撤",  fundStatsService.getMaxDrawdown(code))
        ));

        // 持仓
        p.setHoldings(holdingRepo.findByFund_Code(code).stream().map(h -> {
            FundProfileResponse.Holding ho = new FundProfileResponse.Holding();
            ho.setName(h.getAsset());
            ho.setPercent(h.getPercent());
            return ho;
        }).collect(Collectors.toList()));

        // 业绩归因
        p.setAttribution(attributionRepo.findByFund_Code(code).stream().map(a -> {
            FundProfileResponse.Attribution at = new FundProfileResponse.Attribution();
            at.setCategory(a.getCategory());
            at.setValue(a.getWeight());
            return at;
        }).collect(Collectors.toList()));

        // 公告
        p.setAnnouncements(announcementRepo.findRecentByFund_Code(code).stream().map(a -> {
            FundProfileResponse.Announcement an = new FundProfileResponse.Announcement();
            an.setDate(a.getDate());
            an.setTitle(a.getTitle());
            return an;
        }).collect(Collectors.toList()));

        return p;
    }
}
