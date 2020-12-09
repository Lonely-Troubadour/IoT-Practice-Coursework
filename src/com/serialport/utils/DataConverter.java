package com.serialport.utils;

public class DataConverter {
    public static double temperatureConvert(String str){
        byte[] data = ByteUtils.hexStr2Byte(str);
        double temperature = 0;
        int temp;
        if((data[0]>>7)<0) //为负数
        {
            temp = ((data[0]&0x7f)<<4) + (data[1]>>4);
            temp = ((~temp)&0x7FF) + 0x01; //转换为了具体量化值
            temperature = temp/16.0;
            temperature = (-1)*temperature;
        }
        else //为正数
        {
            temp = (data[0]<<4) + (data[1]>>4);
            temperature = temp/16.0;
        }
        return temperature;
    }

    public static double humiliationConvert(String str, double temperature) {
        byte[] data = ByteUtils.hexStr2Byte(str);
        long num;
        double volt;
        double humiliation = 0;

        num = data[0];
        num = (num<<8)+data[1];
        volt = (num/4096)*3.3;
        if(5<temperature && temperature<50){
            humiliation = ((volt-0.8)*100)/3.1; // 得出相对湿度值相对湿度值
        }else if (temperature<5) {
            humiliation = ((volt-0.8)*100)/3.27; // 得 出相对湿度值高于 50 摄氏度时，选择 85 摄氏度直线。
        }else{
            humiliation = ((volt-0.8)*100)/2.7; //得出相对湿度值
        }

        return humiliation;
    }

    public static double illuminationConvert(String str) {
        byte[] data = ByteUtils.hexStr2Byte(str);
        double illumination = 0;
        int tmp;
        tmp = data[0]+(data[1]<<8);
        illumination =(double) (tmp/65536.0 *16000);
        return illumination;
    }
}
