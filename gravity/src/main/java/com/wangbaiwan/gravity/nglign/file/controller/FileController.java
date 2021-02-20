package com.wangbaiwan.gravity.nglign.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 附件上传、下载
 *
 * @author 往事，优雅而已
 * @date 2019-07-27
 */
@Controller
@RequestMapping("/eyeoftwilight/file")
public class FileController
{
	@RequestMapping
	public String file()
	{
		return "/eyeoftwilight/file/attachfile";
	}

	@RequestMapping("/download/{fileType}/{relationId}")
	public void fileDownload(@PathVariable("relationId") Integer relationId, @PathVariable("fileType") String fileType, HttpServletResponse response) throws IOException
	{
		String fileName = "说明书";
		String filePath = "C:/Users/往事，优雅而已/Desktop/新建文本文档.txt";

		// 1.Content-disposition是 MIME 协议的扩展,它会激活文件下载对话框,它的文件名框自动填充了头中指定的文件名
		// 否则只会在浏览器中打开，而不会以附件的形式下载
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		// 2.必须设置mimeType，否则下载的文件没有后缀,只会有个名字
		// 设置文件ContentType类型:multipart/form-data，这样设置，会自动判断下载文件类型,不推荐，有时会有问题。
		String mimeType = "txt";
		response.setContentType(mimeType);

		ServletOutputStream outputStream = response.getOutputStream();
		FileInputStream fileInputStream = new FileInputStream(filePath);
		BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, len);
		}
		inputStream.close();
	}

	/**
	 * 文件上传
	 * <p>
	 * <p>
	 * 获取当前项目路径:String filePath = ResourceUtils.getURL("").getPath();
	 * <p>
	 * SpringBoot中默认上传最大单个附件大小为：1Mb
	 * 修改的话，需要在yml中配置如：multipart.maxFileSize=10Mb，multipart.maxRequestSize=100Mb
	 * 若想要不限制文件上传的大小，那么就把两个值都设置为-1即可
	 *
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file)
	{
		if (file.isEmpty())
		{
			return "上传失败，因为文件是空的.";
		}

		// contentType用于在文件下载时使用
		String fileName = file.getOriginalFilename();
		String contentType = file.getContentType();
		try
		{
			// 1.文件上传路径,若文件夹不存在，则先创建
			String filePath = "C:/Users/往事，优雅而已/Desktop/新建文件夹";
			File file1 = new File(filePath);
			if (!file1.exists())
			{
				file1.mkdirs();
			}

			// 2.数据写入文件夹
			FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + fileName);
			BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
			outputStream.write(file.getBytes());
			outputStream.flush();
			outputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "上传失败," + e.getMessage();
		}
		return "上传成功";
	}
}
