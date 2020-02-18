//package com.sakinramazan.micros.organizationschedulingpro.Event;
//
//
//import com.sakinramazan.micros.organizationschedulingpro.controller.EventController;
//import com.sakinramazan.micros.organizationschedulingpro.dao.EventRepository;
//import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
//import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
//import com.sakinramazan.micros.organizationschedulingpro.service.impl.EventServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(EventController.class)
//public class EventServiceTest {
//
//    @Mock
//    private EventRepository eventRepository;
//
//    @InjectMocks // auto inject eventRepository
//    private EventService eventService = new EventServiceImpl();
//
//    @Test
//    public void testGetEventById() {
//
//        Event event = new Event();
//        event.setSubject("Sample Event");
//        event.setDuration(30);
//
//        assertThat(event.getSubject())
//                .isEqualTo(eventService.getEvent(14).getSubject());
//
//    }
//}
