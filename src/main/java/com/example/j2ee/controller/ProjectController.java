package com.example.j2ee.controller;

import com.example.j2ee.entity.Project;
import com.example.j2ee.service.ProjectService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Tag(name = "项目")
public class ProjectController
{
    private final HttpSession session;
    @Autowired
    private ProjectService projectService;

    ProjectController(HttpSession session)
    {
        this.session = session;
    }

    @Operation(summary = "上传一个项目")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    @PutMapping(value = "/project/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProject(@Parameter(description = "项目名称") @RequestParam String name,
                                              @Parameter(description = "上传者邮箱") @RequestParam String uploaderEmail,
                                              @Parameter(description = "负责人") @RequestParam String maintainer,
                                              @Parameter(description = "通道ID") @RequestParam int channelId,
                                              @Parameter(description = "项目描述") @RequestParam String description,
                                              @Parameter(description = "负责单位") @RequestParam String company,
                                              @Parameter(description = "金额") @RequestParam int money,
                                              @Parameter(description = "上传时间(yyyy-MM-dd HH:mm:ss)")
                                                  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                                  @RequestParam Timestamp setTime,
                                              @Parameter(description = "项目开始时间") @RequestParam int startYear,
                                              @Parameter(description = "截图(<16MB)") @RequestPart MultipartFile fig,
                                              @Parameter(description = "Zip(<4GB)") @RequestPart MultipartFile zip)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("email").equals(uploaderEmail))
            try
            {
                InputStream isFig = fig.getInputStream();
                InputStream isZip = zip.getInputStream();
                Project project = new Project(uploaderEmail, name, maintainer, channelId, description, company, money,
                                              setTime, "Waiting", startYear);
                projectService.uploadProject(project, isFig, isZip);
                return ResponseEntity.status(200).body(null);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "更新一个项目(通过项目id索引)")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @PostMapping(value = "/project/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProject(@Parameter(description = "项目id") @RequestParam int id,
                                              @Parameter(description = "项目名称") @RequestParam String name,
                                              @Parameter(description = "上传者邮箱") @RequestParam String uploaderEmail,
                                              @Parameter(description = "负责人") @RequestParam String maintainer,
                                              @Parameter(description = "项目描述") @RequestParam String description,
                                              @Parameter(description = "所属通道id") @RequestParam int channelId,
                                              @Parameter(description = "负责单位") @RequestParam String company,
                                              @Parameter(description = "金额") @RequestParam int money,
                                              @Parameter(description = "上传时间(yyyy-MM-dd HH:mm:ss)")
                                                  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                                  @RequestParam Timestamp setTime,
                                              @Parameter(description = "项目开始时间") @RequestParam int startYear,
                                              @Parameter(description = "截图(<16MB)", required = false)
                                                  @RequestPart(required = false) MultipartFile fig,
                                              @Parameter(description = "Zip(<4GB)", required = false)
                                                  @RequestPart(required = false) MultipartFile zip)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("email").equals(uploaderEmail) || session.getAttribute("isAdmin").equals("1"))
            try
            {
                InputStream isFig;
                InputStream isZip;
                if (fig == null)
                    isFig = null;
                else
                    isFig = fig.getInputStream();

                if (zip == null)
                    isZip = null;
                else
                    isZip = zip.getInputStream();
                Project project = new Project(id, uploaderEmail, name, maintainer, channelId, description, company,
                                              money, setTime,
                                              "Waiting", startYear);
                if (projectService.updateProject(project, isFig, isZip) == 0)
                    return ResponseEntity.status(404).body(null);
                return ResponseEntity.status(200).body(null);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "查询一个项目(通过项目名索引)，仅有管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(value = "/project/queryByUploaderEmail")
    public ResponseEntity<List<Project>> queryProject(@Parameter(description = "项目名称") @RequestParam String name)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else
            try
            {
                List<Project> project = projectService.getProjectByName(name);
                if (project.size() == 0)
                    return ResponseEntity.status(404).body(null);
                return ResponseEntity.status(200).body(project);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
    }

    @Operation(summary = "查询一个项目(通过上传者邮箱索引)，管理员与上传者可以使用")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(value = "/project/queryByName")
    public ResponseEntity<List<Project>> queryProjectByUploader(
            @Parameter(description = "上传者邮箱") @RequestParam String uploaderEmail)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("email").equals(uploaderEmail) || session.getAttribute("isAdmin").equals("1"))
            try
            {
                List<Project> project = projectService.getProjectByEmail(uploaderEmail);
                if (project.size() == 0)
                    return ResponseEntity.status(404).body(null);
                return ResponseEntity.status(200).body(project);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "下载一个项目的截图，管理员与上传者可以使用")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200", description = "OK", content = @Content(mediaType = "image/*")),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(value = "/project/download/{id}/fig", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> downloadProject(@Parameter(description = "项目id") @PathVariable int id)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin").equals("1") || session.getAttribute("email")
                .equals(projectService.getProjectEmailById(id)))
        {
            try
            {
                InputStream fig = projectService.getFig(id);
                if (fig == null)
                    return ResponseEntity.status(404).body(null);
                return ResponseEntity.status(200).body(new InputStreamResource(fig));
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "下载一个项目的Zip，管理员与上传者可以使用")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(value = "/project/download/{id}/zip", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadProjectZip(
            @Parameter(description = "项目id") @PathVariable int id) throws UnsupportedEncodingException
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (projectService.isExist(id) == 0)
            return ResponseEntity.status(404).body(null);
        else if (session.getAttribute("isAdmin").equals("1") || session.getAttribute("email")
                .equals(projectService.getProjectEmailById(id)))
        {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Disposition",
                                "attachment; filename=" + URLEncoder.encode(projectService.getProjectNameById(id),
                                                                            "UTF-8") + ".zip");
            try
            {
                InputStream zip = projectService.getZip(id);
                if (zip == null)
                    return ResponseEntity.status(404).body(null);
                return ResponseEntity.status(200).headers(responseHeaders).body(new InputStreamResource(zip));
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "删除一个项目,用户本人以及管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @DeleteMapping(value = "/project/delete")
    public ResponseEntity<Void> deleteProject(@Parameter(description = "项目id") @RequestParam int id)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (projectService.isExist(id) == 0)
            return ResponseEntity.status(404).body(null);
        else if (session.getAttribute("isAdmin").equals("1") || session.getAttribute("email")
                .equals(projectService.getProjectEmailById(id)))
        {
            try
            {
                if (projectService.deleteProject(id) == 1)
                    return ResponseEntity.status(200).body(null);
                else
                    return ResponseEntity.status(404).body(null);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "获取所有项目，管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    @GetMapping(value = "/project/queryAll")
    public ResponseEntity<List<Project>> queryAll()
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin").equals("1"))
            try
            {
                return ResponseEntity.status(200).body(projectService.getAllProject());
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        else
            return ResponseEntity.status(403).body(null);
    }


}