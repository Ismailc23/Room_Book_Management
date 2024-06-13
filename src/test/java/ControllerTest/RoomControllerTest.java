package ControllerTest;

import com.customer.Entity.RoomEntity;
import com.customer.contoller.RoomController;
import com.customer.services.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Test
    public void testCreateRoomcreated() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomNumber(101);
        roomEntity.setPrice(100);
        roomEntity.setType("Standard");
        roomEntity.setAvailable(true);
        when(roomService.createRoom(roomEntity)).thenReturn(roomEntity);
        ResponseEntity<?> response = roomController.createRoom(roomEntity);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(roomEntity, responseBody.get("room"));
        assertEquals(null, responseBody.get("error"));
    }

    //@Test
    public void testCreateRoomNotCreated() {
        RoomEntity roomEntity = new RoomEntity();
        when(roomService.createRoom(roomEntity)).thenReturn(null);
        ResponseEntity<?> response = roomController.createRoom(roomEntity);
        assertEquals(null, response);
    }
}
