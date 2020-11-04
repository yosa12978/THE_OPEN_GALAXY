package com.yosa.theopengalaxy.services;

import com.yosa.theopengalaxy.domain.Project;
import com.yosa.theopengalaxy.dtos.SortType;
import com.yosa.theopengalaxy.exceptions.NotFoundException;
import com.yosa.theopengalaxy.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project getById(Long id){
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found."));
    }

    public List<Project> getLastProjects(int count){
        return projectRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream().limit(Long.valueOf(count)).collect(Collectors.toList());
    }

    public Page<Project> getRecentProject(int page){
        return projectRepository.findAll(
                PageRequest.of(page, 25, Sort.by(Sort.Direction.DESC, "id")));
    }

    public Page<Project> getByTag(String tag, int page, SortType sortType){
        return projectRepository.findByTags(tag,
                PageRequest.of(page, 25, Sort.by(Sort.Direction.valueOf(sortType.name()), "id")));
    }

    public Page<Project> getByAuthor(String username, int page, SortType sortType){
        return projectRepository.findByAuthorUsername(username,
                PageRequest.of(page, 25, Sort.by(Sort.Direction.valueOf(sortType.name()), "id")));
    }

    public Page<Project> search(String query, int page, SortType sort){
        return projectRepository.search(query,
                PageRequest.of(page, 25, Sort.by(Sort.Direction.fromString(sort.name()), "views") ));
    }

    public Page<Project> getRecommended(int page){
        return projectRepository.findByRateGreaterThanEqual(-2L,
                PageRequest.of(page, 25, Sort.by("rate").ascending().and(Sort.by(Sort.Direction.DESC, "id"))));
    }

    public void increaseViews(Long project_id) {
        Project project = projectRepository.findById(project_id)
                .orElseThrow(() -> new NotFoundException("Project not found."));
        project.setViews(project.getViews()+1);
        projectRepository.save(project);
    }

    public List<Project> getTop(){
        return projectRepository.findAll(Sort.by(Sort.Direction.DESC, "rate").and(Sort.by(Sort.Direction.DESC, "id")))
                                .stream()
                                .limit(10)
                                .collect(Collectors.toList());
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public void deleteProject(Long id, String username){
        Project project = projectRepository.findByIdAndAuthorUsername(id, username)
                .orElseThrow(() -> new NotFoundException("Project not found."));
        if(project.getImage() != null){
            File file = new File("D:\\spring boot apps\\theopengalaxy\\src\\main\\resources\\static\\previews\\" + Arrays.toString(project.getImage().split("/previews/")));
            file.delete();
        }
        projectRepository.delete(project);
    }
}
