package com.oocl.ita.web.Service;

import com.oocl.ita.web.common.constant.CharityEventParticipationStatus;
import com.oocl.ita.web.domain.po.Charity.CharityEvent;
import com.oocl.ita.web.domain.po.Charity.CharityEventParticipation;
import com.oocl.ita.web.domain.po.Charity.CharityEventParticipationKey;
import com.oocl.ita.web.repository.CharityEventParticipationRepository;
import com.oocl.ita.web.repository.CharityEventRepository;
import com.oocl.ita.web.service.CharityEventService;
import com.oocl.ita.web.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CharityEventServiceTest {

    @Mock
    private CharityEventRepository charityEventRepository;

    @Mock
    private CharityEventParticipationRepository charityEventParticipationRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CharityEventService charityEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_valid_event_id_when_get_event_by_id_then_return_event() {
        // given
        Integer eventId = 1;
        CharityEvent charityEvent = new CharityEvent();
        when(charityEventRepository.getById(eventId)).thenReturn(charityEvent);

        // when
        CharityEvent result = charityEventService.getById(eventId);

        // then
        assertEquals(charityEvent, result);
        verify(charityEventRepository).getById(eventId);
    }

    @Test
    void given_invalid_event_id_when_get_event_by_id_then_return_null() {
        // given
        Integer eventId = 1;
        when(charityEventRepository.findById(eventId)).thenReturn(Optional.empty());

        // when
        CharityEvent result = charityEventService.getById(eventId);

        // then
        assertNull(result);
        verify(charityEventRepository).getById(eventId);
    }

    @Test
    void given_valid_user_id_when_get_charity_event_participation_by_userid_then_return_participations() {
        // given
        Integer userId = 1;
        CharityEventParticipation participation = new CharityEventParticipation();
        when(charityEventParticipationRepository.findAllByUserId(userId)).thenReturn(List.of(participation));

        // when
        List<CharityEventParticipation> result = charityEventService.getCharityEventParticipationByUserid(userId);

        // then
        assertEquals(1, result.size());
        verify(charityEventParticipationRepository).findAllByUserId(userId);
    }

    @Test
    void given_valid_user_id_and_event_id_when_register_charity_event_then_return_participation() {
        // given
        Integer userId = 1;
        Integer charityEventId = 1;
        boolean claimPoint = true;
        CharityEvent charityEvent = new CharityEvent();
        charityEvent.setCurrentEnrolled(5);
        when(charityEventRepository.getById(charityEventId)).thenReturn(charityEvent);
        when(charityEventParticipationRepository.save(any(CharityEventParticipation.class))).thenReturn(new CharityEventParticipation(userId, charityEventId, CharityEventParticipationStatus.REGISTERED, claimPoint));

        // when
        CharityEventParticipation result = charityEventService.registerCharityEvent(userId, charityEventId, claimPoint);

        // then
        assertNotNull(result);
        assertEquals(CharityEventParticipationStatus.REGISTERED, result.getStatus());
        verify(charityEventRepository).updateCurrentEnrolledById(charityEventId, 6);
        verify(charityEventParticipationRepository).save(any(CharityEventParticipation.class));
    }

    @Test
    void given_valid_event_id_when_get_charity_event_participation_by_event_id_then_return_participations() {
        // given
        Integer charityEventId = 1;
        CharityEventParticipation participation = new CharityEventParticipation();
        when(charityEventParticipationRepository.findAllByCharityEventId(charityEventId)).thenReturn(List.of(participation));

        // when
        List<CharityEventParticipation> result = charityEventService.getCharityEventParticipationByCharityEventId(charityEventId);

        // then
        assertEquals(1, result.size());
        verify(charityEventParticipationRepository).findAllByCharityEventId(charityEventId);
    }

    @Test
    void given_valid_user_id_and_event_id_and_status_when_update_charity_event_participation_status_then_return_updated_participation() {
        // given
        Integer userId = 1;
        Integer charityEventId = 1;
        CharityEventParticipationStatus status = CharityEventParticipationStatus.COMPLETED;
        CharityEventParticipation participation = new CharityEventParticipation(userId, charityEventId, CharityEventParticipationStatus.REGISTERED, true);
        when(charityEventParticipationRepository.getById(any(CharityEventParticipationKey.class))).thenReturn(participation);
        when(charityEventRepository.getById(charityEventId)).thenReturn(new CharityEvent());
        when(charityEventParticipationRepository.save(participation)).thenReturn(participation);

        // when
        CharityEventParticipation result = charityEventService.updateCharityEventParticipationStatus(userId, charityEventId, status);

        // then
        assertEquals(status, result.getStatus());
        verify(charityEventParticipationRepository).getById(any(CharityEventParticipationKey.class));
        verify(charityEventParticipationRepository).save(participation);
    }

    @Test
    void given_valid_event_id_and_user_id_when_quit_charity_event_then_update_enrollment_and_delete_participation() {
        // given
        Integer eventId = 1;
        Integer userId = 1;
        CharityEvent charityEvent = new CharityEvent();
        charityEvent.setCurrentEnrolled(5);
        when(charityEventRepository.getById(eventId)).thenReturn(charityEvent);

        // when
        charityEventService.quitCharityEvent(eventId, userId);

        // then
        verify(charityEventRepository).updateCurrentEnrolledById(eventId, 4);
        verify(charityEventParticipationRepository).deleteById(any(CharityEventParticipationKey.class));
    }
}