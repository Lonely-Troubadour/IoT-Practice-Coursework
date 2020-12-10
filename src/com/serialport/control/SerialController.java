/*
 * MainFrame.java
 *
 * Created on 2016.8.19
 */

package com.serialport.control;

import com.serialport.exception.*;
import com.serialport.manage.SerialPortManager;
import com.serialport.utils.ByteUtils;
import com.serialport.utils.DataConverter;
import com.serialport.utils.ShowUtils;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Date;

public class SerialController {    
	//两个串口
	private SerialPort WSNserialport;
	private SerialPort Arduinoserialport;
	private static final int WSNbaudrate = 115200;
	private static final String WSNcommName = "COM6";
	private static final int Arduinobaudrate = 9600;
	private static final String ArduinocommName = "COM5";
	private OnDataAvailableListener onDataAvailableListener;

	public SerialController() {
		openWSNSerialPort();
		openArduinoSerialPort();
	}

	private void openWSNSerialPort() {
		try {
			WSNserialport = SerialPortManager.openPort(WSNcommName, WSNbaudrate);
			if (WSNserialport != null) {
				System.out.println("成功打开WSN串口");
			}
		} catch (SerialPortParameterFailure e) {
			e.printStackTrace();
		} catch (NotASerialPort e) {
			e.printStackTrace();
		} catch (NoSuchPort e) {
			e.printStackTrace();
		} catch (PortInUse e) {
			e.printStackTrace();
		}
		try {
			SerialPortManager.addListener(WSNserialport, new WSNSerialListener());
		} catch (TooManyListeners e) {
			e.printStackTrace();
		}
	}

	private class WSNSerialListener implements SerialPortEventListener {
		/**
		 * serialEvent
		 * 处理监控到的串口事件 Handle monitored serial port events
		 */
		public void serialEvent(SerialPortEvent serialPortEvent) {
			switch (serialPortEvent.getEventType()) {
			case SerialPortEvent.BI: // 10 通讯中断
				ShowUtils.errorMessage("与串口设备通讯中断");
				break;
			case SerialPortEvent.OE: // 7 溢位（溢出）错误
			case SerialPortEvent.FE: // 9 帧错�?
			case SerialPortEvent.PE: // 8 奇偶校验错误
			case SerialPortEvent.CD: // 6 载波�?�?
			case SerialPortEvent.CTS: // 3 清除待发送数�?
			case SerialPortEvent.DSR: // 4 待发送数据准备好�?
			case SerialPortEvent.RI: // 5 振铃指示
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
				break;
			case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
				byte[] data = null;
				try {
					if (WSNserialport == null) {
						ShowUtils.errorMessage("串口对象为空！监听失败！");
					} else {
						// 读取串口数据
						data = SerialPortManager.readFromPort(WSNserialport);

						Message m = parseMessage(ByteUtils.byteArrayToHexString(data, false));
						String shortAddr = m.getShortAddress();
						double temperature = DataConverter.temperatureConvert(m.getTemperature());
						double humiliation = DataConverter.humiliationConvert(m.getHumiliation(), temperature);
						double illumination = DataConverter.illuminationConvert(m.getIllumination());
						System.out.println("温度："+temperature+"，湿度："+humiliation+"，光照："+illumination);
						
						onDataAvailableListener.onDataAvailable(shortAddr, temperature, humiliation, illumination);
						sendData(Arduinoserialport, m.getTemperature()+m.getHumiliation()+m.getIllumination());
					}
				} catch (Exception e) {
					ShowUtils.errorMessage(e.toString());
					System.exit(0);
				}
				break;
			}
		}
	}
	
	public interface OnDataAvailableListener {
		void onDataAvailable(String shortAddr, double temperature, double humiliation, double illumination);
	}
	
	public void setOnDataAvailableListener(OnDataAvailableListener onDataAvailableListener) {
		this.onDataAvailableListener = onDataAvailableListener;
	}

	private void openArduinoSerialPort() {
		try {
			Arduinoserialport = SerialPortManager.openPort(ArduinocommName, Arduinobaudrate);
			if (Arduinoserialport != null) {
				System.out.println("成功打开Arduino串口");
			}
		} catch (SerialPortParameterFailure e) {
			e.printStackTrace();
		} catch (NotASerialPort e) {
			e.printStackTrace();
		} catch (NoSuchPort e) {
			e.printStackTrace();
		} catch (PortInUse e) {
			e.printStackTrace();
		}
//		try {
//			SerialPortManager.addListener(Arduinoserialport, new ArduinoSerialListener());
//		} catch (TooManyListeners e) {
//			e.printStackTrace();
//		}
	}

//	private class ArduinoSerialListener implements SerialPortEventListener {
//		/**
//		 * serialEvent
//		 * 处理监控到的串口事件 Handle monitored serial port events
//		 */
//		public void serialEvent(SerialPortEvent serialPortEvent) {
//			switch (serialPortEvent.getEventType()) {
//				case SerialPortEvent.BI: // 10 通讯中断
//					ShowUtils.errorMessage("与串口设备�?�讯中断");
//					break;
//				case SerialPortEvent.OE: // 7 溢位（溢出）错误
//				case SerialPortEvent.FE: // 9 帧错�?
//				case SerialPortEvent.PE: // 8 奇偶校验错误
//				case SerialPortEvent.CD: // 6 载波�?�?
//				case SerialPortEvent.CTS: // 3 清除待发送数�?
//				case SerialPortEvent.DSR: // 4 待发送数据准备好�?
//				case SerialPortEvent.RI: // 5 振铃指示
//				case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
//					break;
//				case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
//					byte[] data = null;
//					try {
//						if (Arduinoserialport == null) {
//							ShowUtils.errorMessage("串口对象为空！监听失败！");
//						} else {
//							// 读取串口数据
//							data = SerialPortManager.readFromPort(Arduinoserialport);
//
//							System.out.println(ByteUtils.fromHexString(ByteUtils.byteArrayToHexString(data, false)));
//						}
//					} catch (Exception e) {
//						ShowUtils.errorMessage(e.toString());
//						System.exit(0);
//					}
//					break;
//			}
//		}
//	}

	public void closeSerialPort(SerialPort serialport) {
		SerialPortManager.closePort(serialport);
		System.out.println(serialport.getName() + "串口已关�?");
		serialport = null;
	}

	private void sendData(SerialPort serialport, String data) {
		try {
			SerialPortManager.sendToPort(serialport,
					ByteUtils.hexStr2Byte(data));
		} catch (SendDataToSerialPortFailure e) {
			e.printStackTrace();
		} catch (SerialPortOutputStreamCloseFailure e) {
			e.printStackTrace();
		}
	}

	private Message parseMessage(String data) {
		Date date = new Date();
		return new Message(date, data.substring(0, 4), data.substring(4, 8), data.substring(8, 24), data.substring(24, 28), data.substring(28, 32), 
				data.substring(32, 36), data.substring(36, 40), data.substring(40, 44), data.substring(44, 48), data.substring(48, 52));
	}

}
