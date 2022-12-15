package GradeManagement.GradeManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import GradeManagement.GradeManagement.model.GradeTable;

import GradeManagement.GradeManagement.service.GradeTableService;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class GradeTableController {

  @Autowired

  private GradeTableService gradeTableService;


  @Autowired
  private HonorRepository honorRepository;


  @GetMapping("/courses/{courseId}/students/{studentId}/grade")
  public ResponseEntity<List<GradeTable>> getAllgradeBystudentIdAndCourseId(@PathVariable(value = "studentId") Long studentId,@PathVariable(value = "courseId") Long courseId) {
    return gradeTableService.getAllgradeBystudentIdAndCourseId(studentId,courseId);
  }

  @GetMapping("/grade/{id}")
  public ResponseEntity<GradeTable> getgradeById(@PathVariable(value = "id") Long id) {
    return gradeTableService.getgradeById(id);
  }

  @GetMapping("/grade")
  public ResponseEntity<List<GradeTable>> getAllGrades() {
      return gradeTableService.getAllGrades();
  }

  @PostMapping("/courses/{courseId}/students/{studentId}/grade")
  public ResponseEntity<GradeTable> createGradeTable(@PathVariable(value = "courseId") Long courseId,@PathVariable(value = "studentId") Long studentId,
      @RequestBody GradeTable gradeTableRequest) {
    return gradeTableService.createGradeTable(courseId,studentId,gradeTableRequest);
  }




  @PutMapping("/grade/{id}")
  public ResponseEntity<GradeTable> updateGradeTable(@PathVariable("id") long id, @RequestBody GradeTable gradeTableRequest) {
    return gradeTableService.updateGradeTable(id, gradeTableRequest);
  }


  @PutMapping("/grade/{courseID}/{studentID}/{SchoolYear}/{semester}")
  public ResponseEntity<GradeTable> updateGrade(@PathVariable("courseID") long courseId,@PathVariable("studentID") long studentId,@PathVariable("SchoolYear") Integer schoolYear,@PathVariable("semester") String semester,@RequestBody GradeTable gradeTableRequest) {
    return gradeTableService.updateGrade(courseId,studentId,schoolYear,semester,gradeTableRequest);
  }

  @DeleteMapping("/grade/{id}")
  public ResponseEntity<HttpStatus> deleteGradeTable(@PathVariable("id") long id) {
    return gradeTableService.deleteGradeTable(id);
  }
  
  @DeleteMapping("/courses/{coursesId}/students/{studentId}/grade")
  public ResponseEntity<List<GradeTable>> deleteGradeTableByCourseIdAndStudentId(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "courseId") Long courseId) {
    return gradeTableService.deleteGradeTableByCourseIdAndStudentId(courseId,studentId);
  }
}
