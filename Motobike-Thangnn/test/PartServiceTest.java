package service;

import dao.PartDAO;
import java.math.BigDecimal;
import model.Part;
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
 * PURE UNIT TESTS for PartService (Sheet addPart, updatePart, deactivatePart).
 *
 * Characteristics:
 * - 100% Isolated: Runs on RAM, zero database connection required.
 * - PartDAO is mocked using Mockito.
 * - Business validations (name, unit length, negative quantity, negative price, id <= 0) are tested directly.
 */
class PartServiceTest {

    private static final String PREFIX = "ut_";
    private static final int NOT_EXIST_ID = 999999;

    private PartDAO mockDao;
    private PartService service;

    @BeforeEach
    void setUp() {
        mockDao = mock(PartDAO.class);
        service = new PartService(mockDao);
    }

    private static Part part(String name, String unit, String price, int quantity) {
        return new Part(0, name, "desc", unit, price == null ? null : new BigDecimal(price), quantity, true);
    }

    private static String nameOfLength(int length) {
        return PREFIX + "a".repeat(length - PREFIX.length());
    }

    // =========================================================================
    // 1. insert() Tests (Sheet: addPart)
    // =========================================================================

    @Test
    @DisplayName("addPart UTCID01 [N] valid data -> returns new id, data saved")
    void addPart_validData_returnsIdAndSaves() {
        Part p = part(PREFIX + "Loc nhot", "pcs", "35000", 40);
        when(mockDao.insert(p)).thenReturn(101);

        int id = service.insert(p);

        assertEquals(101, id);
        verify(mockDao).insert(p);
    }

    @Test
    @DisplayName("addPart UTCID02 [B] name = 100 chars (max) -> success")
    void addPart_nameExactly100Chars_success() {
        Part p = part(nameOfLength(100), "pcs", "35000", 40);
        when(mockDao.insert(p)).thenReturn(1);

        int result = service.insert(p);

        assertTrue(result > 0);
        verify(mockDao).insert(p);
    }

    @Test
    @DisplayName("addPart UTCID03 [B] name = 101 chars -> returns -1 (rejected by Service)")
    void addPart_name101Chars_returnsMinusOne() {
        Part p = part(nameOfLength(101), "pcs", "35000", 40);

        int result = service.insert(p);

        assertEquals(-1, result);
        verify(mockDao, never()).insert(any());
    }

    @Test
    @DisplayName("addPart UTCID04 [B] unit = 20 chars (max) -> success")
    void addPart_unitExactly20Chars_success() {
        Part p = part(PREFIX + "Loc nhot", "u".repeat(20), "35000", 40);
        when(mockDao.insert(p)).thenReturn(2);

        int result = service.insert(p);

        assertTrue(result > 0);
        verify(mockDao).insert(p);
    }

    @Test
    @DisplayName("addPart UTCID05 [B] unit = 21 chars -> returns -1 (rejected by Service)")
    void addPart_unit21Chars_returnsMinusOne() {
        Part p = part(PREFIX + "Loc nhot", "u".repeat(21), "35000", 40);

        int result = service.insert(p);

        assertEquals(-1, result);
        verify(mockDao, never()).insert(any());
    }

    @Test
    @DisplayName("addPart UTCID06 [A] name = null -> returns -1 (rejected by Service)")
    void addPart_nullName_returnsMinusOne() {
        Part p = part(null, "pcs", "35000", 40);

        int result = service.insert(p);

        assertEquals(-1, result);
        verify(mockDao, never()).insert(any());
    }

    @Test
    @DisplayName("addPart UTCID07 [B] quantity = 0 -> success")
    void addPart_quantityZero_success() {
        Part p = part(PREFIX + "Loc nhot", "pcs", "35000", 0);
        when(mockDao.insert(p)).thenReturn(3);

        int id = service.insert(p);

        assertTrue(id > 0);
        verify(mockDao).insert(p);
    }

    @Test
    @Tag("known-defect")
    @DisplayName("addPart UTCID08 [A] negative quantity -> should return -1 (DAO has no check)")
    void addPart_negativeQuantity_shouldBeRejected() {
        Part p = part(PREFIX + "Loc nhot", "pcs", "35000", -5);
        when(mockDao.insert(p)).thenReturn(1);

        int result = service.insert(p);

        assertEquals(-1, result);
    }

    @Test
    @Tag("known-defect")
    @DisplayName("addPart UTCID09 [A] negative price -> should return -1 (DAO has no check)")
    void addPart_negativePrice_shouldBeRejected() {
        Part p = part(PREFIX + "Loc nhot", "pcs", "-1000", 40);
        when(mockDao.insert(p)).thenReturn(1);

        int result = service.insert(p);

        assertEquals(-1, result);
    }

    // =========================================================================
    // 2. update() Tests (Sheet: updatePart)
    // =========================================================================

    @Test
    @DisplayName("updatePart UTCID01 [N] existing id, valid data -> true, data changed")
    void updatePart_validData_returnsTrueAndSaves() {
        Part changed = new Part(1, PREFIX + "new part", "new desc", "bo", new BigDecimal("99000"), 25, false);
        when(mockDao.update(changed)).thenReturn(true);

        boolean result = service.update(changed);

        assertTrue(result);
        verify(mockDao).update(changed);
    }

    @Test
    @DisplayName("updatePart UTCID02 [A] id does not exist -> false")
    void updatePart_idNotExist_returnsFalse() {
        Part p = new Part(NOT_EXIST_ID, PREFIX + "new part", "new desc", "bo", new BigDecimal("99000"), 25, true);
        when(mockDao.update(p)).thenReturn(false);

        boolean result = service.update(p);

        assertFalse(result);
        verify(mockDao).update(p);
    }

    @Test
    @DisplayName("updatePart UTCID03 [A] name = null -> false, DAO never called")
    void updatePart_nullName_returnsFalseAndKeepsData() {
        Part bad = new Part(1, null, "new desc", "bo", new BigDecimal("99000"), 25, true);

        boolean result = service.update(bad);

        assertFalse(result);
        verify(mockDao, never()).update(any());
    }

    @Test
    @DisplayName("updatePart UTCID04 [B] unit = 21 chars -> false, DAO never called")
    void updatePart_unit21Chars_returnsFalse() {
        Part bad = new Part(1, PREFIX + "new part", "new desc", "u".repeat(21), new BigDecimal("99000"), 25, true);

        boolean result = service.update(bad);

        assertFalse(result);
        verify(mockDao, never()).update(any());
    }

    // =========================================================================
    // 3. deactivate() Tests (Sheet: deactivatePart)
    // =========================================================================

    @Test
    @DisplayName("deactivatePart UTCID01 [N] existing id -> true")
    void deactivatePart_existingId_returnsTrue() {
        when(mockDao.deactivate(10)).thenReturn(true);

        boolean result = service.deactivate(10);

        assertTrue(result);
        verify(mockDao).deactivate(10);
    }

    @Test
    @DisplayName("deactivatePart UTCID02 [A] id does not exist -> false")
    void deactivatePart_idNotExist_returnsFalse() {
        when(mockDao.deactivate(NOT_EXIST_ID)).thenReturn(false);

        boolean result = service.deactivate(NOT_EXIST_ID);

        assertFalse(result);
        verify(mockDao).deactivate(NOT_EXIST_ID);
    }

    @Test
    @DisplayName("deactivatePart UTCID03 [B] id = 0 and id = -1 -> false (rejected by Service)")
    void deactivatePart_zeroAndNegativeId_returnsFalse() {
        assertFalse(service.deactivate(0));
        assertFalse(service.deactivate(-1));
        verify(mockDao, never()).deactivate(anyInt());
    }
}