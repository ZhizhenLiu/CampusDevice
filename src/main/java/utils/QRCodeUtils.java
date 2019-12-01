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
import java.nio.file.Path;
import java.util.HashMap;

public class QRCodeUtils
{
    public static void main(String[] args)
    {
        int width = 300;    //定义宽和高
        int height = 300;

        String format = "png";    //图片格式
        String content = "Hello World!";    //存储内容

        HashMap hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    //字符集，包含中文的话就要utf-8
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);    //纠错等级，等级越高图片越不清晰
        hints.put(EncodeHintType.MARGIN, 2);    //边距

        try
        {
            BitMatrix bitMatrix = new MultiFormatWriter().
                    encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            Path file = new File("/E:/code.png").toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        }
        catch (WriterException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
