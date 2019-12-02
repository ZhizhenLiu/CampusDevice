package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class QRCodeUtils
{
    /*
     * @Description: 生成存储内容的二维码,返回存在服务器的图片的url
     * @Param content
     * @Return: java.lang.String
     */
    public static String createQRCode(String content, String path)
    {
        int width = 300;    //定义宽和高
        int height = 300;
        //这里如果想自动跳转的话，content需要加上https,如: "https://github.com/ZhizhenLiu/CampusDevice.git";

        String format = "png";    //图片格式

        HashMap hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    //字符集，包含中文的话就要utf-8
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);    //纠错等级，等级越高图片越不清晰
        hints.put(EncodeHintType.MARGIN, 2);    //边距
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        path = path + "img/code";
        String codeUrl = "http://49.235.73.29:8083/img/code";
        try
        {
            BitMatrix bitMatrix = new MultiFormatWriter().
                    encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            File file = new File(path);

            // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
            if (!file.exists())
            {
                file.mkdirs();
            }
            path = path + "/code-" + dateFormat.format(date) + ".png";
            codeUrl = codeUrl + "/code-" + dateFormat.format(date) + ".png";
            file = new File(path);
            MatrixToImageWriter.writeToPath(bitMatrix, format, file.toPath());
        }
        catch (IOException | WriterException e)
        {
            e.printStackTrace();
        }

        return codeUrl;
    }

}
