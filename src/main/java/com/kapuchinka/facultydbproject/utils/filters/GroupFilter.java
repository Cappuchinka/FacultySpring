package com.kapuchinka.facultydbproject.utils.filters;

import com.kapuchinka.facultydbproject.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GroupFilter {
    private Short groupNum;
    private Short groupSem;
    private Short groupYear;

    public GroupFilter() {
        this.groupNum = -1;
        this.groupSem = -1;
        this.groupYear = -1;
    }

    public Short getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Short groupNum) {
        this.groupNum = groupNum;
    }

    public Short getGroupSem() {
        return groupSem;
    }

    public void setGroupSem(Short groupSem) {
        this.groupSem = groupSem;
    }

    public Short getGroupYear() {
        return groupYear;
    }

    public void setGroupYear(Short groupYear) {
        this.groupYear = groupYear;
    }

    public List<Group> filter(List<Group> groups) {
        if (groupNum != null && groupNum != -1){
            Stream<Group> stream = groups.stream();
            groups = stream.filter(g-> g.groupNum != null && g.groupNum.equals(groupNum)).toList();
        }

        if (groupSem != null && groupSem != -1){
            Stream<Group> stream = groups.stream();
            groups = stream.filter(g-> g.groupSem != null && g.groupSem.equals(groupSem)).toList();
        }

        if (groupYear != null && groupYear != -1){
            Stream<Group> stream = groups.stream();
            groups = stream.filter(g-> g.groupYear != null && g.groupYear.equals(groupYear)).toList();
        }

        return groups;
    }

    public Page<Group> filter(Page<Group> groups) {
        if (groupNum != null && groupNum != -1){
            Stream<Group> stream = groups.stream();
            groups = new PageImpl<>(stream.filter(g-> g.groupNum != null && g.groupNum.equals(groupNum)).toList());
        }

        if (groupSem != null && groupSem != -1){
            Stream<Group> stream = groups.stream();
            groups = new PageImpl<>(stream.filter(g-> g.groupSem != null && g.groupSem.equals(groupSem)).toList());
        }

        if (groupYear != null && groupYear != -1){
            Stream<Group> stream = groups.stream();
            groups = new PageImpl<>(stream.filter(g-> g.groupYear != null && g.groupYear.equals(groupYear)).toList());
        }

        return groups;
    }

    public Page<Group> filter(Iterable<Group> groups, Short offset, Short limit){
        Stream<Group> stream = StreamSupport.stream(groups.spliterator(), false);
        List<Group> groupList = stream.toList();
        groupList = filter(groupList);
        Page<Group> groupPage = new PageImpl<>(groupList, PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "groupId")), groupList.size());
        return groupPage;
    }

    public boolean isReady(){
        return !Objects.equals(groupNum, -1) || !Objects.equals(groupSem, -1) || !Objects.equals(groupYear, -1);
    }
}
