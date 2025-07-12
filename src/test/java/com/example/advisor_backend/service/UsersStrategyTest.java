package com.example.advisor_backend.service;

import com.example.advisor_backend.exception.BusinessException;
import com.example.advisor_backend.model.entity.Strategy;
import com.example.advisor_backend.model.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersStrategyTest {
    @Mock
    private UsersStrategyService usersStrategyService;

    @Test
    void testGetStrategiesByUser() {
        User user = new User();
        List<Strategy> strategies = Arrays.asList(new Strategy());
        when(usersStrategyService.getStrategiesByUser(user)).thenReturn(strategies);
        List<Strategy> result = usersStrategyService.getStrategiesByUser(user);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetStrategiesByUser_nullUser() {
        when(usersStrategyService.getStrategiesByUser(null)).thenReturn(Collections.emptyList());
        List<Strategy> result = usersStrategyService.getStrategiesByUser(null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetStrategiesById() {
        Strategy strategy = new Strategy();
        when(usersStrategyService.getStrategiesById(1)).thenReturn(strategy);
        Strategy result = usersStrategyService.getStrategiesById(1);
        assertNotNull(result);
    }

    @Test
    void testStartStrategy_success_alreadyActive() {
        doNothing().when(usersStrategyService).startStrategy(1);
        assertDoesNotThrow(() -> usersStrategyService.startStrategy(1));
        doThrow(new BusinessException(400, "策略已运行", null)).when(usersStrategyService).startStrategy(2);
        assertThrows(BusinessException.class, () -> usersStrategyService.startStrategy(2));
    }

    @Test
    void testStopStrategy_success_alreadyStopped() {
        doNothing().when(usersStrategyService).stopStrategy(1);
        assertDoesNotThrow(() -> usersStrategyService.stopStrategy(1));
        doThrow(new BusinessException(400, "策略已停止", null)).when(usersStrategyService).stopStrategy(2);
        assertThrows(BusinessException.class, () -> usersStrategyService.stopStrategy(2));
    }

    @Test
    void testDeleteStrategy_active_notExist_nullId() {
        doNothing().when(usersStrategyService).deleteStrategy(1);
        assertDoesNotThrow(() -> usersStrategyService.deleteStrategy(1));
        doThrow(new BusinessException(404, "策略不存在", null)).when(usersStrategyService).deleteStrategy(2);
        assertThrows(BusinessException.class, () -> usersStrategyService.deleteStrategy(2));
        doThrow(new BusinessException(400, "ID为空", null)).when(usersStrategyService).deleteStrategy((Integer) null);
        assertThrows(BusinessException.class, () -> usersStrategyService.deleteStrategy((Integer) null));
    }

    @Test
    void testCreateStrategy_and_nullUser() {
        User user = new User();
        Strategy strategy = new Strategy();
        when(usersStrategyService.createStrategy(user)).thenReturn(strategy);
        Strategy result = usersStrategyService.createStrategy(user);
        assertNotNull(result);
        when(usersStrategyService.createStrategy(null)).thenReturn(null);
        assertNull(usersStrategyService.createStrategy(null));
    }
} 