package com.fest.pecfestBackend.service;

import com.fest.pecfestBackend.dto.EditEventDTO;
import com.fest.pecfestBackend.dto.RegisterEventDTO;
import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.response.GenericApiDataResponse;
import com.fest.pecfestBackend.response.GenericApiResponse;

import java.util.List;
import java.util.Map;

public interface IEventService {

    GenericApiResponse editEvent(EditEventDTO editEventDTO);

    GenericApiResponse registerEvent(RegisterEventDTO registerEventDTO);

    GenericApiDataResponse<Map<String, Map<String, List<Event>>>> getEventsData();
}
