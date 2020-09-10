package com.fest.pecfestBackend.controller;

import com.fest.pecfestBackend.entity.Event;
import com.fest.pecfestBackend.entity.Team;
import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.enums.EventCount;
import com.fest.pecfestBackend.repository.EventRepo;
import com.fest.pecfestBackend.repository.TeamRepo;
import com.fest.pecfestBackend.repository.UserRepo;
import com.fest.pecfestBackend.service.ExcelFileExporter;
import com.fest.pecfestBackend.service.UserService;
import com.google.common.base.CharMatcher;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DownloadExcelController {

    @Autowired
    private ExcelFileExporter excelFileExporter;

    @Autowired
    private UserService userService;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private TeamRepo teamRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/download/users")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        ByteArrayInputStream stream = excelFileExporter.contactListToExcelFile(userService.getUser().getData());
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/download/accommodationUsers")
    public void downloadAccommodationCsv(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=accommodation.xlsx");
        ByteArrayInputStream stream = excelFileExporter.contactListToExcelFile(userService.getUsersWithAccommodation().getData());
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/download/participants/{id}")
    public void downloadEventUsersCsv(@PathVariable("id") Long eventId,HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=participants.xlsx");
        Optional<Event> eventOptional=eventRepo.findById(eventId);
        if(eventOptional.isPresent()){
            if(eventOptional.get().getEventCount()== EventCount.INDIVIDUAL) {
                List<Team> teamList=teamRepo.findAllByEventId(eventId);
                List<Long> idList=new ArrayList<>();
                for(Team team:teamList)
                    idList.addAll(team.getMemberPecFestIdList().parallelStream().map(pecFestId->Long.valueOf(CharMatcher.inRange('0','9').retainFrom(pecFestId))).collect(Collectors.toList()));
                ByteArrayInputStream stream = excelFileExporter.contactListToExcelFile(userRepo.findAllById(idList));
                IOUtils.copy(stream, response.getOutputStream());
            }
            else{
                List<Team> teamList=teamRepo.findAllByEventId(eventId);
                Map<Team,List<User>> teams= new HashMap<>();

                for(Team team:teamList) {
                    List<Long> idList=new ArrayList<>();
                    idList.addAll(team.getMemberPecFestIdList().parallelStream().map(pecFestId->Long.valueOf(CharMatcher.inRange('0','9').retainFrom(pecFestId))).collect(Collectors.toList()));
                    teams.put(team,userRepo.findAllById(idList));
                }
                ByteArrayInputStream stream = excelFileExporter.contactTeamListToExcelFile(teams);
                IOUtils.copy(stream, response.getOutputStream());
            }
        }

    }
}
