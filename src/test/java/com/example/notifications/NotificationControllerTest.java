package com.example.notifications;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationPublisher notificationPublisher;

    @Test
    void createsAndListsNotificationsForCustomer() throws Exception {
        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"customerId":"cust-42","message":"Your order has shipped"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value("cust-42"))
                .andExpect(jsonPath("$.id").exists());
            verify(notificationPublisher).publish(any(Notification.class));

        mockMvc.perform(get("/api/notifications/customer/cust-42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Your order has shipped"));
    }

    @Test
    void rejectsRequestsWithBlankFields() throws Exception {
        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"customerId":" ","message":""}
                                """))
                .andExpect(status().isBadRequest());
    }
}
