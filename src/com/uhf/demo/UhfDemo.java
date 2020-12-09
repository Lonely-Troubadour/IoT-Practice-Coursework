package com.uhf.demo;

import com.uhf.linkage.Linkage;
import com.uhf.structures.RwData;
import com.uhf.utils.StringUtils;

public class UhfDemo {
	private String portName;
	private DatabaseController dbController;
	
	public UhfDemo(String portName, DatabaseController dbController) {
		this.portName = portName;
		this.dbController = dbController;
		init();
	}

	public void init(){
		if(Linkage.initial(this.portName) == 0)
			System.out.println("connect success");
		else
			System.out.println("connect failed");
	}

	public void deinit() {
		Linkage.deinit();
	}

	public String epcReadSync(int start_location, int length) {
		byte[] password = StringUtils.stringToByte("00000000");
		RwData rwData = new RwData();
		while (true) {
			int status = Linkage.getInstance().readTagSync(password, 1, start_location, length, 3000, rwData);
			if (status == 0) {
				if (rwData.status == 0) {
					String result = "";
					String epc = "";
					if (rwData.rwDataLen > 0) {
						result = StringUtils.byteToHexString(rwData.rwData,
								rwData.rwDataLen);
					}
					if (rwData.epcLen > 0) {
						epc = StringUtils
								.byteToHexString(rwData.epc, rwData.epcLen);
					}
					
					String checkResult = this.dbController.checkIdentity(epc);
					if(checkResult!=null){
						System.out.println("result====" + result);// 3200
						System.out.println("epc====" + epc);// 320030007F263000DDD90140
						System.out.println("read success");
						return checkResult;
					}
				}
			}
		}
	}

	public void epcWriteSync(int start_location, int length, String data) {
		byte[] password = StringUtils.stringToByte("00000000");
		byte[] writeData = StringUtils.stringToByte(data);
		RwData rwData = new RwData();
		while(true) {
			int status = Linkage.getInstance().writeTagSync(password, 1, start_location, length, writeData, 3000, rwData);
			if (status == 0) {
				if (rwData.status == 0) {
					String epc = "";
					if (rwData.epcLen > 0) {
						epc = StringUtils
								.byteToHexString(rwData.epc, rwData.epcLen);
					}
					System.out.println("epc====" + epc);
					System.out.println("epc write success");
					return;
				}
			}
		}
	}

	public String tidReadSync(int start_location, int length) {
		RwData rwData = new RwData();
		byte[] password = StringUtils.stringToByte("00000000");

		while(true){
			int status = Linkage.getInstance().readTagSync(password, 2, start_location, length, 3000, rwData);
			if (status == 0) {
				String result = "";
				String epc = "";
				if (rwData.status == 0) {
					if (rwData.rwDataLen > 0) {
						result = StringUtils.byteToHexString(rwData.rwData,
								rwData.rwDataLen);
					}
					if (rwData.epcLen > 0) {
						epc = StringUtils
								.byteToHexString(rwData.epc, rwData.epcLen);
					}
					System.out.println("tidData====" + result);
					System.out.println("epc====" + epc);
					System.out.println("tid read success");
					return result;
				}
			}
		}

		//System.out.println("tid read failed");
	}

	public String userReadSync(String target_epc, int start_location, int length) {
		RwData rwData = new RwData();
		byte[] password = StringUtils.stringToByte("00000000");

		while (true) {
			int status = Linkage.getInstance().readTagSync(password, 3, start_location, length, 3000, rwData);
			if (status == 0) {
				String result = "";
				String epc = "";
				if (rwData.status == 0) {
					if (rwData.rwDataLen > 0) {
						result = StringUtils.byteToHexString(rwData.rwData,
								rwData.rwDataLen);
					}
					if (rwData.epcLen > 0) {
						epc = StringUtils
								.byteToHexString(rwData.epc, rwData.epcLen);
					}
					if (target_epc != null) {
						if (target_epc.equals(epc)) {
							System.out.println("userData====" + result);
							System.out.println("epc====" + epc);
							System.out.println("user read success");
							return result;
						}
					} else {
						System.out.println("userData====" + result);
						System.out.println("epc====" + epc);
						System.out.println("user read success");
						return result;
					}
				}
			}
		}
		//		System.out.println("user read failed");
	}

	public String userWriteSync(int start_location, int length, String data) {
		byte[] password = StringUtils.stringToByte("00000000");
		byte[] writeData = StringUtils.stringToByte(data);
		RwData rwData = new RwData();

		while (true) {
			int status = Linkage.getInstance().writeTagSync(password, 3, start_location, length, writeData, 3000, rwData);
			if (status == 0) {
				if (rwData.status == 0) {
					String epc = "";
					if (rwData.epcLen > 0) {
						epc = StringUtils
								.byteToHexString(rwData.epc, rwData.epcLen);
					}
					System.out.println("epc====" + epc);
					System.out.println("user write success");

					return epc;
				}
			}
		}
		//		System.out.println("user write failed");
	}
}
