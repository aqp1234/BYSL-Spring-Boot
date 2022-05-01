package com.kms.byslboot.calendar.mapper;

import static com.kms.byslboot.member.service.SessionLoginServiceImpl.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.mock.web.MockHttpSession;

import com.kms.byslboot.calendar.dto.CalendarRequest;
import com.kms.byslboot.calendar.dto.CalendarResponse;
import com.kms.byslboot.calendar.entity.Calendar;
import com.kms.byslboot.calendar.exception.CalendarNotFoundException;
import com.kms.byslboot.workspace.entity.UserWorkspace;
import com.kms.byslboot.workspace.mapper.UserWorkspaceMapper;
import com.kms.byslboot.workspace.service.UserWorkspaceService;
import com.kms.byslboot.workspace.service.UserWorkspaceServiceImpl;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CalendarMapperTest {
	
	@Autowired
	private CalendarMapper calendarMapper;

	@Mock
	private UserWorkspaceServiceImpl userWorkspaceService;
	
	private MockHttpSession session;
	private CalendarRequest calendarRequest;
	private CalendarRequest calendarRequestUpdate;
	private Calendar calendar;
	private Calendar calendarUpdate;
	private UserWorkspace userWorkspace;
	
	@BeforeEach
	void setup() {
		session = new MockHttpSession();
		session.setAttribute(MEMBER_ID, 11);
		
		userWorkspace = UserWorkspace.builder()
							.id(2)
							.userId(11)
							.workspaceId(3)
							.teamId(49)
							.nick("김민석")
							.color("#ffa3a3")
							.build();
		
		calendarRequest = CalendarRequest.builder()
								.subject("subjectTest")
								.content("contentTest")
								.startDate("2022-05-01")
								.endDate("2022-05-02")
								.build();
		
		when(userWorkspaceService.findConnectedUserWorkspace(any(int.class))).thenReturn(userWorkspace);
		
		calendar = calendarRequest.toEntity(calendarRequest, userWorkspace.getWorkspaceId(), userWorkspaceService);
	}
	
	@Test
	@DisplayName("캘린더 추가 테스트")
	void insertCalendar() {
		calendarMapper.insertCalendar(calendar);
		assertThat(calendarMapper.findCalendarById(calendar.getId())).isNotNull();
	}
	
	@Test
	@DisplayName("캘린더 Id값으로 조회 테스트")
	void findCalendarById() {
		calendarMapper.insertCalendar(calendar);
		CalendarResponse calendarResponse = calendarMapper.findCalendarById(calendar.getId()).orElseThrow(CalendarNotFoundException::new);
		assertThat(calendarResponse.getSubject()).isEqualTo(calendarRequest.getSubject());
		assertThat(calendarResponse.getContent()).isEqualTo(calendarRequest.getContent());
		assertThat(calendarResponse.getStartDate()).isEqualTo(Date.valueOf(calendarRequest.getStartDate()));
		assertThat(calendarResponse.getEndDate()).isEqualTo(Date.valueOf(calendarRequest.getEndDate()));
		assertThat(calendarResponse.getOwnerId()).isEqualTo(userWorkspace.getUserId());
		assertThat(calendarResponse.getWorkspaceId()).isEqualTo(userWorkspace.getWorkspaceId());
		assertThat(calendarResponse.getColor()).isEqualTo(userWorkspace.getColor());
		assertThat(calendarResponse.getNick()).isEqualTo(userWorkspace.getNick());
	}
	
	@Test
	@DisplayName("날짜를 기준으로 조회 테스트")
	void findCalendarByDate() {
		calendarMapper.insertCalendar(calendar);
		calendarMapper.insertCalendar(calendar);
		CalendarRequest calendarRequestEx = CalendarRequest.builder()
												.startDate("2022-04-30")
												.endDate("2022-05-03")
												.build();
		Calendar calendarEx = calendarRequestEx.toEntity(calendarRequestEx, userWorkspace.getWorkspaceId(), userWorkspaceService);
		assertThat(calendarMapper.findCalendarByDate(calendarEx).size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("날짜를 기준으로 조회 테스트2")
	void findCalendarByMonth() {
		calendarMapper.insertCalendar(calendar);
		calendarMapper.insertCalendar(calendar);
		CalendarRequest calendarRequestEx = CalendarRequest.builder()
												.startDate("2022-04-01")
												.endDate("2022-04-30")
												.build();
		Calendar calendarEx = calendarRequestEx.toEntity(calendarRequestEx, userWorkspace.getWorkspaceId(), userWorkspaceService);
		assertThat(calendarMapper.findCalendarByDate(calendarEx).size()).isEqualTo(0);
	}
	
	@Test
	@DisplayName("날짜를 기준으로 조회 테스트2")
	void findCalendarByMonth2() {
		calendarMapper.insertCalendar(calendar);
		calendarMapper.insertCalendar(calendar);
		CalendarRequest calendarRequestEx = CalendarRequest.builder()
												.startDate("2022-05-01")
												.endDate("2022-05-31")
												.build();
		Calendar calendarEx = calendarRequestEx.toEntity(calendarRequestEx, userWorkspace.getWorkspaceId(), userWorkspaceService);
		assertThat(calendarMapper.findCalendarByDate(calendarEx).size()).isEqualTo(2);
	}
	
	@Test
	@DisplayName("캘린더 업데이트 테스트")
	void updateCalendar() {
		calendarMapper.insertCalendar(calendar);
		calendarRequestUpdate = CalendarRequest.builder()
								.subject("subjectUpdate")
								.content("contentUpdate")
								.startDate("2022-05-03")
								.endDate("2022-05-05")
								.build();
		calendarUpdate = calendarRequest.toEntity(calendarRequestUpdate, calendar.getId(), userWorkspace.getWorkspaceId(), userWorkspaceService);

		calendarMapper.updateCalendar(calendarUpdate);
		CalendarResponse calendarResponseEx = calendarMapper.findCalendarById(calendarUpdate.getId()).orElseThrow(CalendarNotFoundException::new);
		assertThat(calendarResponseEx.getSubject()).isEqualTo(calendarRequestUpdate.getSubject());
		assertThat(calendarResponseEx.getContent()).isEqualTo(calendarRequestUpdate.getContent());
		assertThat(calendarResponseEx.getStartDate()).isEqualTo(Date.valueOf(calendarRequestUpdate.getStartDate()));
		assertThat(calendarResponseEx.getEndDate()).isEqualTo(Date.valueOf(calendarRequestUpdate.getEndDate()));
	}
	
	@Test
	@DisplayName("캘린더 삭제 테스트")
	void deleteCalendar() {
		calendarMapper.insertCalendar(calendar);
		calendarMapper.deleteCalendarById(calendar.getId());
		assertThat(calendarMapper.findCalendarById(calendar.getId())).isEmpty();
	}
}
