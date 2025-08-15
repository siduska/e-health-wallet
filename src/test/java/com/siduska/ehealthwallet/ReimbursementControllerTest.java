
package com.siduska.ehealthwallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siduska.ehealthwallet.conf.ReimbursementNotFoundException;
import com.siduska.ehealthwallet.controller.ReimbursementController;
import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.service.ReimbursementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReimbursementController.class)
public class ReimbursementControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ReimbursementService reimbursementService;

        ReimbursementDto dto1 = ReimbursementDto.builder()
            .id(1L).status("PENDING").patientName("John").medicalProcedure("tracheotomy")
            .identificationNumber("1234567890").cost(BigDecimal.valueOf(100))
            .build();
        ReimbursementDto dto2 = ReimbursementDto.builder()
            .id(2L).status("APPROVED").patientName("Johnny johny, yes papa").medicalProcedure("lobotomy")
            .identificationNumber("1234567890").cost(BigDecimal.valueOf(120))
            .build();
        CreateReimbursementRequest createRequest = CreateReimbursementRequest.builder()
            .patientName("John")
            .medicalProcedure("medicalProcedure")
            .identificationNumber("1234567890")
            .cost(BigDecimal.valueOf(100))
            .status("PENDING").build();
        UpdateReimbursementRequest updateRequest = UpdateReimbursementRequest.builder()
            .description("description")
            .status("APPROVED").build();
        Long invalidId = 789L;
        Long validId = 1L;
        String notFoundMessage = "Reimbursement with id " + invalidId + " not found.";
        String invalidStatus = "INVALID";
        String expectedInvalidStatusMessage = "Invalid status: INVALID. Allowed statuses: [PENDING, APPROVED, REJECTED]";

    @WithMockUser(value = "spring")
    @Test
    public void get_AllReimbursements_returns_ReimbursementsList_Ok() throws Exception {

            given(reimbursementService.getAllReimbursements())
                    .willReturn(List.of(dto1, dto2));

            mockMvc.perform(MockMvcRequestBuilders.get("/reimbursements")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[1].id").value(2));
    }

    @WithMockUser(value = "spring")
    @Test
    public void setValidStatus_get_AllReimbursementsByStatus_returns_filteredReimbursementsList_Ok() throws Exception {
        String status = "PENDING";
        given(reimbursementService.getAllReimbursementsByStatus(status))
                .willReturn(List.of(dto1));

        mockMvc.perform(MockMvcRequestBuilders.get("/reimbursements/filter")
                        .param("filter", status)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("PENDING"));

    }

    @WithMockUser(value = "spring")
    @Test
    public void setInvalidStatus_get_AllReimbursementsByStatus_returns_BadRequest() throws Exception {

        willThrow(new IllegalArgumentException("Invalid status: " + invalidStatus +
                ". Allowed statuses: " + Arrays.toString(StatusEnum.values())))
                .given(reimbursementService).getAllReimbursementsByStatus(invalidStatus);

        mockMvc.perform(MockMvcRequestBuilders.get("/reimbursements/filter")
                        .param("filter", invalidStatus)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedInvalidStatusMessage));
    }

    @WithMockUser(value = "spring")
    @Test
    public void setValidId_getReimbursementById_returns_ReimbursementDto_Ok() throws Exception {

        given(reimbursementService.getReimbursementById(validId))
                .willReturn(dto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/reimbursements/" + validId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(validId));
    }

    @WithMockUser(value = "spring")
    @Test
    public void setInvalidId_getReimbursementById_returns_BadRequest() throws Exception {

        willThrow(new ReimbursementNotFoundException(invalidId))
                .given(reimbursementService).getReimbursementById(invalidId);

        mockMvc.perform(MockMvcRequestBuilders.get("/reimbursements/" + invalidId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(notFoundMessage));
    }

    @WithMockUser(value = "spring")
    @Test
    public void setValid_createReimbursement_returns_ReimbursementDto_Created() throws Exception {

        Mockito.when(reimbursementService.createReimbursement(createRequest)).thenReturn(dto1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reimbursements")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(asJsonString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(validId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.identificationNumber").value("1234567890"));
    }

    @WithMockUser(value = "spring")
    @Test
    public void setInvalidStatus_createReimbursement_returns_ReimbursementDto_BadRequest() throws Exception {
        createRequest.setStatus(invalidStatus);
        willThrow(new IllegalArgumentException("Invalid status: " + invalidStatus +
                ". Allowed statuses: " + Arrays.toString(StatusEnum.values())))
                .given(reimbursementService).createReimbursement(createRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reimbursements")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(asJsonString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "spring")
    @Test
    public void setValid_updateReimbursement_returns_ReimbursementDto_Ok() throws Exception {

        dto1.setStatus("APPROVED");
        Mockito.when(reimbursementService.updateReimbursement(validId, updateRequest)).thenReturn(dto1);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reimbursements/" + validId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(asJsonString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(validId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("APPROVED"));
    }

    @WithMockUser(value = "spring")
    @Test
    public void setInvalidStatus_updateReimbursement_returns_ReimbursementDto_BadRequest() throws Exception {

        updateRequest.setStatus(invalidStatus);
        willThrow(new IllegalArgumentException("Invalid status: " + invalidStatus +
                ". Allowed statuses: " + Arrays.toString(StatusEnum.values())))
                .given(reimbursementService).updateReimbursement(validId, updateRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reimbursements/" + validId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(asJsonString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "spring")
    @Test
    public void setInvalidId_updateReimbursement_returns_ReimbursementDto_NotFound() throws Exception {

        willThrow(new ReimbursementNotFoundException(invalidId))
                .given(reimbursementService).updateReimbursement(invalidId, updateRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/reimbursements/" + invalidId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(asJsonString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "spring")
    @Test
    public void setValid_deleteReimbursement_returns_NoContent() throws Exception {

        willDoNothing().given(reimbursementService).deleteById(validId);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reimbursements/" + validId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "spring")
    @Test
    public void setInvalidId_deleteReimbursement_returns_NotFound() throws Exception {

        willThrow(new ReimbursementNotFoundException(invalidId))
                .given(reimbursementService).deleteById(invalidId);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/reimbursements/" + invalidId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(notFoundMessage));
    }

    public static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
