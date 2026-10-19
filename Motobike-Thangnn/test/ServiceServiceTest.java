package service;

import dao.ServiceDAO;
import java.math.BigDecimal;
import model.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * PURE UNIT TESTS for ServiceService (Sheet addService, updateService, deactivateService).
 *
 * Characteristics:
 * - 100% Isolated: Runs on RAM, zero database connection required.
 * - ServiceDAO is mocked using Mockito.
 * - Business validations (name length, nulls, negative price, id <= 0) are tested directly.
 */
class ServiceServiceTest {

    private static final String PREFIX = "ut_";
    private static final int NOT_EXIST_ID = 999999;

    private ServiceDAO mockDao;
    private ServiceService service;

    @BeforeEach
    void setUp() {
        mockDao = mock(ServiceDAO.class);
        service = new ServiceService(mockDao);
    }

    private static Service svc(String name, String desc, String price) {
        return new Service(0, name, desc, price == null ? null : new BigDecimal(price), true);
    }

    private static String nameOfLength(int length) {
        return PREFIX + "a".repeat(length - PREFIX.length());
    }

    // =========================================================================
    // 1. insert() Tests (Sheet: addService)
    // =========================================================================

    @Test
    @DisplayName("addService UTCID01 [N] valid data -> returns new id, data saved")
    void addService_validData_returnsIdAndSaves() {
        Service valid = svc(PREFIX + "Thay nhot", "desc", "120000");
        when(mockDao.insert(valid)).thenReturn(10);

        int id = service.insert(valid);

        assertEquals(10, id);
        verify(mockDao).insert(valid);
    }

    @Test
    @DisplayName("addService UTCID02 [B] name = 100 chars (max) -> success")
    void addService_nameExactly100Chars_success() {
        Service s = svc(nameOfLength(100), "desc", "120000");
        when(mockDao.insert(s)).thenReturn(1);

        int result = service.insert(s);

        assertTrue(result > 0);
        verify(mockDao).insert(s);
    }

    @Test
    @DisplayName("addService UTCID03 [B] name = 101 chars -> returns -1 (rejected by Service)")
    void addService_name101Chars_returnsMinusOne() {
        Service s = svc(nameOfLength(101), "desc", "120000");

        int result = service.insert(s);

        assertEquals(-1, result);
        verify(mockDao, never()).insert(any());
    }

    @Test
    @DisplayName("addService UTCID04 [A] name = null -> returns -1 (rejected by Service)")
    void addService_nullName_returnsMinusOne() {
        Service s = svc(null, "desc", "120000");

        int result = service.insert(s);

        assertEquals(-1, result);
        verify(mockDao, never()).insert(any());
    }

    @Test
    @DisplayName("addService UTCID05 [A] price = null -> returns -1 (rejected by Service)")
    void addService_nullPrice_returnsMinusOne() {
        Service s = svc(PREFIX + "Thay nhot", "desc", null);

        int result = service.insert(s);

        assertEquals(-1, result);
        verify(mockDao, never()).insert(any());
    }

    @Test
    @DisplayName("addService UTCID06 [B] price = 0 -> success")
    void addService_priceZero_success() {
        Service s = svc(PREFIX + "Thay nhot", "desc", "0");
        when(mockDao.insert(s)).thenReturn(5);

        int result = service.insert(s);

        assertTrue(result > 0);
        verify(mockDao).insert(s);
    }

    @Test
    @Tag("known-defect")
    @DisplayName("addService UTCID07 [A] negative price -> should return -1 (DAO has no check)")
    void addService_negativePrice_shouldBeRejected() {
        Service s = svc(PREFIX + "Thay nhot", "desc", "-5000");
        when(mockDao.insert(s)).thenReturn(1);

        int result = service.insert(s);

        assertEquals(-1, result);
    }

    // =========================================================================
    // 2. update() Tests (Sheet: updateService)
    // =========================================================================

    @Test
    @DisplayName("updateService UTCID01 [N] existing id, valid data -> true, data changed")
    void updateService_validData_returnsTrueAndSaves() {
        Service changed = new Service(1, PREFIX + "new name", "new desc", new BigDecimal("250000"), false);
        when(mockDao.update(changed)).thenReturn(true);

        boolean result = service.update(changed);

        assertTrue(result);
        verify(mockDao).update(changed);
    }

    @Test
    @DisplayName("updateService UTCID02 [A] id does not exist -> false")
    void updateService_idNotExist_returnsFalse() {
        Service s = new Service(NOT_EXIST_ID, PREFIX + "new name", "new desc", new BigDecimal("250000"), true);
        when(mockDao.update(s)).thenReturn(false);

        boolean result = service.update(s);

        assertFalse(result);
        verify(mockDao).update(s);
    }

    @Test
    @DisplayName("updateService UTCID03 [A] name = null -> false, DAO never called")
    void updateService_nullName_returnsFalseAndKeepsData() {
        Service bad = new Service(1, null, "new desc", new BigDecimal("250000"), true);

        boolean result = service.update(bad);

        assertFalse(result);
        verify(mockDao, never()).update(any());
    }

    @Test
    @DisplayName("updateService UTCID04 [B] name = 100 chars -> true")
    void updateService_name100Chars_returnsTrue() {
        Service s = new Service(1, nameOfLength(100), "new desc", new BigDecimal("250000"), true);
        when(mockDao.update(s)).thenReturn(true);

        boolean result = service.update(s);

        assertTrue(result);
        verify(mockDao).update(s);
    }

    @Test
    @DisplayName("updateService UTCID05 [B] name = 101 chars -> false, DAO never called")
    void updateService_name101Chars_returnsFalse() {
        Service s = new Service(1, nameOfLength(101), "new desc", new BigDecimal("250000"), true);

        boolean result = service.update(s);

        assertFalse(result);
        verify(mockDao, never()).update(any());
    }

    // =========================================================================
    // 3. deactivate() Tests (Sheet: deactivateService)
    // =========================================================================

    @Test
    @DisplayName("deactivateService UTCID01 [N] existing id -> true")
    void deactivateService_existingId_returnsTrue() {
        when(mockDao.deactivate(10)).thenReturn(true);

        boolean result = service.deactivate(10);

        assertTrue(result);
        verify(mockDao).deactivate(10);
    }

    @Test
    @DisplayName("deactivateService UTCID02 [A] id does not exist -> false")
    void deactivateService_idNotExist_returnsFalse() {
        when(mockDao.deactivate(NOT_EXIST_ID)).thenReturn(false);

        boolean result = service.deactivate(NOT_EXIST_ID);

        assertFalse(result);
        verify(mockDao).deactivate(NOT_EXIST_ID);
    }

    @Test
    @DisplayName("deactivateService UTCID03 [B] id = 0 and id = -1 -> false (rejected by Service)")
    void deactivateService_zeroAndNegativeId_returnsFalse() {
        assertFalse(service.deactivate(0));
        assertFalse(service.deactivate(-1));
        verify(mockDao, never()).deactivate(anyInt());
    }
}