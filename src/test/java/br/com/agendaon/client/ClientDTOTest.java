package br.com.agendaon.client;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientDTOTest {
    @Test
    public void testCreateClient() {
        ClientDTO clientDTO = new ClientDTO();
        assertNotNull(clientDTO);
        clientDTO.setName("John Doe");
        clientDTO.setCpf("33827584078");
        clientDTO.setAge(10);
        assertNotNull(clientDTO);
        assertEquals("John Doe", clientDTO.getName());
        assertEquals("33827584078", clientDTO.getCpf());
        assertEquals(10, clientDTO.getAge());
    }
}