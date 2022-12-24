package com.kapuchinka.facultydbproject.utils.filters;

import com.kapuchinka.facultydbproject.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SubjectFilter {
    private String subjectName;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectFilter(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectFilter() {
        this.subjectName = "";
    }

    public List<Subject> filter(List<Subject> consumers){

        if (!subjectName.equals("")){
            Stream<Subject> stream = consumers.stream();
            consumers = stream.filter(s->s.subjectName.split(" ")[0].equals(subjectName)).toList();
        }

        return consumers;
    }

    public Page<Subject> filter(Page<Subject> consumers){

        if (!subjectName.equals("")){
            Stream<Subject> stream = consumers.stream();
            consumers = new PageImpl<>(stream.filter(s->s.subjectName.split(" ")[0].equals(subjectName)).toList());
        }

        return consumers;
    }

    public Page<Subject> filter(Iterable<Subject> subjects, Short offset, Short limit){
        Stream<Subject> stream = StreamSupport.stream(subjects.spliterator(), false);
        List<Subject> subjectList = stream.toList();
        subjectList = filter(subjectList);
        Page<Subject> subjectPage = new PageImpl<>(subjectList, PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id")), subjectList.size());
        return subjectPage;
    }

    public boolean isReady(){
        return !Objects.equals(subjectName, "");
    }
}
