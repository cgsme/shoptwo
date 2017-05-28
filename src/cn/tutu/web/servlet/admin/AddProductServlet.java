package cn.tutu.web.servlet.admin;

import cn.tutu.domain.Category;
import cn.tutu.domain.Product;
import cn.tutu.service.ProductService;
import cn.tutu.utils.CommonsUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加商品
 *
 * Created by 曹贵生 on 2017/5/26.
 * Email: 1595143088@qq.com
 */
@WebServlet("/admin/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // 文件上传

            // 创建磁盘文件项目工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 创建文件上传核心对象
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解析request
            List<FileItem> fileItemList = upload.parseRequest(request);
            // 保存字段信息的map，用来个BeanUtils封装数据
            Map<String, String> map = new HashMap<>();
            // 遍历文件项
            for (FileItem fileItem : fileItemList) {
                boolean formField = fileItem.isFormField();
                // 判断是否是表单字段
                if (formField) {   // 是表单字段
                    String fieldName = fileItem.getFieldName();    // 获取表单字段的name
                    System.out.println("字段名：" + fieldName);
                    String fieldValue = fileItem.getString();   // 获取表单字段的值
                    map.put(fieldName, fieldValue);
                } else {    // 是文件，不是表单字段
                    String fileName = fileItem.getName();// 获取文件名
                    System.out.println("文件名：" + fileName);
                    String uploadPath = this.getServletContext().getRealPath("upload");
                    InputStream inputStream = fileItem.getInputStream();
                    System.out.println("上传路径：" + uploadPath);

                    OutputStream outputStream = new FileOutputStream(uploadPath + "/" + fileName);
                    IOUtils.copy(inputStream, outputStream);
                    inputStream.close();
                    outputStream.close();

                    // 报存文件路径到Map中
                    map.put("pimage", "upload/" + fileName);
                }

            }
            // 封装数据到实体中
            Product product = new Product();
            BeanUtils.populate(product, map);
            // 封装其他数据
            product.setPid(CommonsUtils.getUUID());   // 封装pid
            product.setPflag(0);   // 0:上架   1： 未上架
            product.setPdate(new Date());

            // 创建Category类别对象，用于封装到product中
            Category category = new Category();
            BeanUtils.populate(category, map);

            product.setCategory(category);

            // 创建ProductService对象
            ProductService productService = new ProductService();
            // 报错商品
            productService.saveProduct(product);

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
