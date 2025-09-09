package com.siduska.ehealthwallet;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.mapper.ReimbursementMapper;
import com.siduska.ehealthwallet.repository.ReimbursementRepository;
import com.siduska.ehealthwallet.service.ChangeLogService;
import com.siduska.ehealthwallet.service.UserService;
import com.siduska.ehealthwallet.service.impl.ReimbursementServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReimbursementServiceTest {

    @InjectMocks
    private ReimbursementServiceImpl service;

    @Mock
    private ReimbursementRepository reimbursementRepository;

    @Mock
    private ReimbursementMapper reimbursementMapper;

    @Mock
    private ChangeLogService changeLogService;

    @Mock
    private UserService userService;

    ReimbursementDto reimbursementDto = ReimbursementDto.builder()
                                        .id(1L)
                                        .status("PENDING")
                                        .patientName("John")
                                        .identificationNumber("1234567890")
                                        .cost(BigDecimal.valueOf(100)).build();

    Reimbursement reimbursement = Reimbursement.builder()
            .id(1L)
            .status(StatusEnum.PENDING)
            .patientName("John")
            .identificationNumber("1234567890")
            .cost(BigDecimal.valueOf(100)).build();

    @Test
    @DisplayName("Processing valid reimbursement request should return valid dto")
    public void testCreateReimbursementService() {

        CreateReimbursementRequest createReimbursementRequest = CreateReimbursementRequest.builder()
                .status("PENDING")
                .patientName("John")
                .identificationNumber("1234567890")
                .cost(BigDecimal.valueOf(100))
                .build();

        when(service.createReimbursement(createReimbursementRequest))
                .thenReturn(reimbursementDto);

        ReimbursementDto result = service.createReimbursement(createReimbursementRequest);

        assertNotNull(result);
        assertEquals("John", result.getPatientName());
        assertEquals("PENDING", result.getStatus());
        assertEquals("1234567890", result.getIdentificationNumber());
        assertEquals(BigDecimal.valueOf(100), result.getCost());
    }

    @Test
    @DisplayName("Processing valid reimbursement update request should return updated dto" +
            "and changelog should be created")
    public void testUpdateReimbursementService() {
        // Given
        Long id = 1L;

        UpdateReimbursementRequest updateRequest = UpdateReimbursementRequest.builder()
                .status("APPROVED")
                .description("Updated description")
                .build();

        Reimbursement existing = new Reimbursement();
        existing.setId(id);
        existing.setStatus(StatusEnum.PENDING);

        ReimbursementDto expectedDto = ReimbursementDto.builder()
                .id(id)
                .status("APPROVED")
                .build();

        given(reimbursementRepository.findById(id)).willReturn(Optional.of(existing));

        willAnswer(invocation -> {
            UpdateReimbursementRequest req = invocation.getArgument(0);
            Reimbursement entity = invocation.getArgument(1);
            entity.setStatus(StatusEnum.valueOf(req.getStatus()));
            return null;
        }).given(reimbursementMapper).updateReimbursement(any(), any());

        given(reimbursementRepository.save(existing)).willReturn(existing);
        given(userService.getUserName()).willReturn("testUser");
        given(reimbursementMapper.toReimbursementDto(existing)).willReturn(expectedDto);

        ReimbursementDto result = service.updateReimbursement(id, updateRequest);

        assertNotNull(result);
        assertEquals("APPROVED", result.getStatus());

        then(reimbursementRepository).should().findById(id);
        then(reimbursementMapper).should().updateReimbursement(updateRequest, existing);
        then(reimbursementRepository).should().save(existing);
        then(changeLogService).should()
                .createChangeLog("PENDING", "APPROVED", "Updated description", "testUser", existing);
    }

    @Test
    @DisplayName("Processing get all reimbursements should return list of dto")
    public void testGetAllReimbursementsService() {
        Reimbursement test = Reimbursement.builder()
                .id(2L)
                .status(StatusEnum.APPROVED)
                .patientName("Fero")
                .identificationNumber("1234567890")
                .cost(BigDecimal.valueOf(120)).build();
        given(reimbursementRepository.findAll()).willReturn(List.of(reimbursement, test));
        given(reimbursementMapper.toReimbursementDto(reimbursement)).willReturn(reimbursementDto);
        Iterable<ReimbursementDto> result = service.getAllReimbursements();
        assertNotNull(result);
        assertEquals(2, result.spliterator().getExactSizeIfKnown());
        then(reimbursementRepository).should().findAll();
    }

    @Test
    @DisplayName("Processing get all reimbursements should return list of filtered dto")
    public void testGetAllReimbursementsByStatus() {
        given(reimbursementRepository.findAll()).willReturn(List.of(reimbursement));
        given(reimbursementMapper.toReimbursementDto(reimbursement)).willReturn(reimbursementDto);
        Iterable<ReimbursementDto> result = service.getAllReimbursementsByStatus("PENDING");
        assertNotNull(result);
        assertEquals("PENDING", result.iterator().next().getStatus());
        assertEquals(1, result.spliterator().getExactSizeIfKnown());
        then(reimbursementRepository).should().findAll();
    }

    @Test
    @DisplayName("Processing get reimbursement by valid Id should return dto with id")
    public void testGetReimbursementById() {
        Long id = 1L;
        given(reimbursementRepository.findById(id)).willReturn(Optional.of(reimbursement));
        given(reimbursementMapper.toReimbursementDto(reimbursement)).willReturn(reimbursementDto);
        ReimbursementDto result = service.getReimbursementById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(reimbursementDto, result);
        then(reimbursementRepository).should().findById(id);
        then(reimbursementMapper).should().toReimbursementDto(reimbursement);
    }
}
