package com.example.j2ee.controller;

import com.example.j2ee.entity.Thesis;
import com.example.j2ee.service.ThesisService;
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

@Tag(name = "论文接口")
@RestController
public class ThesisController
{
    private final HttpSession session;
    @Autowired
    private ThesisService thesisService;

    public ThesisController(HttpSession session)
    {
        this.session = session;
    }

    @Operation(summary = "新增论文", description = "需要上传者邮箱与登录邮箱一致")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
            })
    @PutMapping(value = "/thesis/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadThesis(@Parameter(description = "论文名称") @RequestParam String name,
                                             @Parameter(description = "上传者邮箱") @RequestParam String uploaderEmail,
                                             @Parameter(description = "负责人") @RequestParam String maintainer,
                                             @Parameter(description = "通道ID") @RequestParam int channelId,
                                             @Parameter(description = "论文描述") @RequestParam String description,
                                             @Parameter(description = "负责单位") @RequestParam String company,
                                             @Parameter(description = "上传时间(yyyy-MM-dd HH:mm:ss)")
                                             @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                             @RequestParam Timestamp uploadTime,
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
                Thesis thesis = new Thesis(uploaderEmail, name, maintainer, channelId, description, company,
                                           uploadTime, "Waiting");
                thesisService.uploadThesis(thesis, isFig, isZip);
                return ResponseEntity.status(200).body(null);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "修改论文", description = "根据论文ID索引，需要上传者本人修改或者管理员修改")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @PostMapping(path = "/thesis/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateThesis(@Parameter(description = "论文ID") @RequestParam int thesisId,
                                             @Parameter(description = "论文名称") @RequestParam String name,
                                             @Parameter(description = "上传者邮箱") @RequestParam String uploaderEmail,
                                             @Parameter(description = "负责人") @RequestParam String maintainer,
                                             @Parameter(description = "通道ID") @RequestParam int channelId,
                                             @Parameter(description = "论文描述") @RequestParam String description,
                                             @Parameter(description = "负责单位") @RequestParam String company,
                                             @Parameter(description = "上传时间(yyyy-MM-dd HH:mm:ss)")
                                             @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                             @RequestParam Timestamp uploadTime,
                                             @Parameter(description = "截图(<16MB)") @RequestPart(required = false)
                                             MultipartFile fig,
                                             @Parameter(description = "Zip(<4GB)") @RequestPart(required = false)
                                             MultipartFile zip)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("email").equals(uploaderEmail))
            try
            {
                InputStream isFig = fig.getInputStream();
                InputStream isZip = zip.getInputStream();
                Thesis thesis = new Thesis(thesisId, uploaderEmail, name, maintainer, channelId, description, company,
                                           uploadTime, "Waiting");
                thesisService.updateThesis(thesis, isFig, isZip);
                return ResponseEntity.status(200).body(null);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "删除论文", description = "根据论文ID索引，需要上传者本人删除或者管理员删除")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @DeleteMapping(path = "/thesis/delete")
    public ResponseEntity<Void> deleteThesis(@Parameter(description = "项目id") @RequestParam int id)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin").equals("1") || session.getAttribute("email")
                .equals(thesisService.getEmailById(id)))
        {
            try
            {
                if (thesisService.deleteProject(id) == 1)
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

    @Operation(summary = "查询论文(通过论文名索引)", description = "仅有管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(path = "/thesis/queryByName")
    public ResponseEntity<List<Thesis>> getThesisByName(@Parameter(description = "论文名") @RequestParam String name)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin").equals("1"))
        {
            try
            {
                List<Thesis> thesisList = thesisService.queryThesisByName(name);
                if (thesisList.size() == 0)
                    return ResponseEntity.status(404).body(null);
                else
                    return ResponseEntity.status(200).body(thesisList);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "查询一个论文(通过上传者邮箱索引)", description = "上传者本人和管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(path = "/thesis/queryByUploaderEmail")
    public ResponseEntity<List<Thesis>> getThesisByUploaderEmail(
            @Parameter(description = "上传者邮箱") @RequestParam String uploaderEmail)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin").equals("1") || session.getAttribute("email").equals(uploaderEmail))
        {
            try
            {
                List<Thesis> thesisList = thesisService.queryThesisByUploaderEmail(uploaderEmail);
                if (thesisList.size() == 0)
                    return ResponseEntity.status(404).body(null);
                else
                    return ResponseEntity.status(200).body(thesisList);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "查询所有论文", description = "仅有管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(path = "/thesis/queryAll")
    public ResponseEntity<List<Thesis>> getAllThesis()
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin").equals("1"))
        {
            try
            {
                List<Thesis> thesisList = thesisService.queryAllThesis();
                if (thesisList.size() == 0)
                    return ResponseEntity.status(404).body(null);
                else
                    return ResponseEntity.status(200).body(thesisList);
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "下载一个论文的Zip", description = "上传者本人和管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(path = "/thesis/download/{id}/zip", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadThesisZip(
            @Parameter(description = "论文ID") @PathVariable int id) throws UnsupportedEncodingException
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (thesisService.isExist(id) == 0)
            return ResponseEntity.status(404).body(null);
        else if (session.getAttribute("isAdmin").equals("1") || session.getAttribute("email")
                .equals(thesisService.getEmailById(id)))
        {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Disposition",
                                "attachment; filename=" + URLEncoder.encode(thesisService.getThesisNameById(id),
                                                                            "UTF-8") + ".zip");
            try
            {
                InputStream zip = thesisService.getZip(id);
                return ResponseEntity.status(200).headers(responseHeaders).body(new InputStreamResource(zip));
            } catch (Exception e)
            {
                return ResponseEntity.status(400).body(null);
            }
        }
        else
            return ResponseEntity.status(403).body(null);
    }

    @Operation(summary = "下载一个论文的截图", description = "上传者本人和管理员可以使用")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "image/*")),
                    @ApiResponse(responseCode = "400", description = "Server Error", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @GetMapping(path = "/thesis/download/{id}/fig", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> downloadThesisFig(@Parameter(description = "论文ID") @PathVariable int id)
    {
        if (session.getAttribute("email") == null)
            return ResponseEntity.status(403).body(null);
        else if (session.getAttribute("isAdmin").equals("1") || session.getAttribute("email")
                .equals(thesisService.getEmailById(id)))
        {
            try
            {
                InputStream fig = thesisService.getFig(id);
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

}