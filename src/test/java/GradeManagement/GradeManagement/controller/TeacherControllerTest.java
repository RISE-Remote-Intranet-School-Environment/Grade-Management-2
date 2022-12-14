package GradeManagement.GradeManagement.controller;

import GradeManagement.GradeManagement.model.Teacher;
import GradeManagement.GradeManagement.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        teacherRepository.deleteAll();
    }

    @Test
    void getAllTeachers() throws Exception {
        // given
        List<Teacher> listOfTeachers = new ArrayList<>();
        listOfTeachers.add(new Teacher("BRASSENS George"));
        listOfTeachers.add(new Teacher("DELON Alain"));
        listOfTeachers.add(new Teacher("PIAF Edith"));
        teacherRepository.saveAll(listOfTeachers);

        // when
        ResultActions response = mockMvc.perform(get("/api/teachers"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfTeachers.size())));
    }

    @Test
    void getTeacherById() throws Exception {
        // given
        Teacher teacher = new Teacher("DUJARDIN Jean");
        teacherRepository.save(teacher);

        // when
        ResultActions response = mockMvc.perform(get("/api/teachers/{id}", teacher.getId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(teacher.getName())));
    }

    @Test
    void getTeacherByInvalidId() throws Exception {
        // given
        Teacher teacher = new Teacher("DUJARDIN Jean");
        teacherRepository.save(teacher);

        // when
        ResultActions response = mockMvc.perform(get("/api/teachers/{id}", teacher.getId()+1));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void addTeacherByCourse() {
    }

    @Test
    void createTeachers() throws Exception {
        // given
        Teacher teacher = new Teacher("DENEUVE Catherine");

        // when
        ResultActions response = mockMvc.perform(post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacher)));

        // then
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(teacher.getName())));
    }

    @Test
    void updateTeacher() throws Exception {
        // given
        Teacher savedTeacher = new Teacher("DES GALLES Chariot");
        teacherRepository.save(savedTeacher);

        Teacher updatedTeacher = new Teacher("DE GAULLE Charles");

        // when
        ResultActions response = mockMvc.perform(put("/api/teachers/{id}", savedTeacher.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTeacher)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedTeacher.getName())));
    }

    @Test
    void updateTeacherInvalidId() throws Exception {
        // given
        Teacher savedTeacher = new Teacher("DES GALLES Chariot");
        teacherRepository.save(savedTeacher);

        Teacher updatedTeacher = new Teacher("DE GAULLE Charles");

        // when
        ResultActions response = mockMvc.perform(put("/api/teachers/{id}", savedTeacher.getId()+1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTeacher)));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void assignCourseToTeacher() {
    }

    @Test
    void deleteTeacher() throws Exception {
        // given
        Teacher savedTeacher = new Teacher("LAGARDE Christine");
        teacherRepository.save(savedTeacher);
        List<Teacher> listOfTeacher = new ArrayList<>();
        listOfTeacher.add(new Teacher("HALLYDAY Johnny"));
        listOfTeacher.add(new Teacher("BREL Jacques"));
        teacherRepository.saveAll(listOfTeacher);

        // when
        ResultActions response = mockMvc.perform(delete("/api/teachers/{id}", savedTeacher.getId()));
        ResultActions get_response = mockMvc.perform(get("/api/teachers"));

        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
        get_response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfTeacher.size())));
    }

    @Test
    void deleteTeacherFromCourse() {
    }
}