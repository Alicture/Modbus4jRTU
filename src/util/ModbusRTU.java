package util;

import org.junit.Test;

import net.wimpi.modbus.*;
import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteMultipleRegistersRequest;
import net.wimpi.modbus.msg.WriteMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.DigitalOut;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.BitVector;
import net.wimpi.modbus.util.SerialParameters;

/**
 * 
 * @author Alicture
 * @version Beta2
 * @date 2016-07-01 23:39:20
 * 
 */
public class ModbusRTU  {
	private SerialParameters sp;
	private SerialConnection myCon = null;
	public int MWordVaue[][] = new int[16][256];
	public boolean MBitvau[][]= new boolean[16][];
	public int unitid = 1;
	public ReadMultipleRegistersRequest rmrreq = null;
	public ReadMultipleRegistersResponse rmres = null;
	public ModbusSerialTransaction trans = null;
	public ReadInputDiscretesRequest ridreq = null;
	public ReadInputDiscretesResponse ridres = null;
	public ReadCoilsRequest rcreq = null;
	public ReadCoilsResponse rcres = null;
	public WriteSingleRegisterRequest wsrreq = null;
	public WriteSingleRegisterResponse wsrres = null;
	public void openCon(String iPortName, int iBaudRate, int iDataBits, String iParity, int iStopBits, String Encoding,
			Boolean Echo) throws Exception {

		SerialParameters sp = new SerialParameters();
		sp.setPortName(iPortName);
		sp.setBaudRate(iBaudRate);
		sp.setDatabits(iDataBits);
		sp.setParity(iParity);
		sp.setStopbits(iStopBits);
		sp.setEncoding(Encoding);
		sp.setEcho(Echo);
		this.sp = sp;
		// if (myCon.isOpen() == true) {
		// myCon.close();
		//
		myCon = new SerialConnection(this.sp);
		myCon.open();

	}

	public void closeCon() {
		myCon.close();
	}

	public void readKeepRegisters(int ref, int count,int addr) throws InterruptedException, ModbusIOException, ModbusSlaveException, ModbusException {
		rmrreq = new ReadMultipleRegistersRequest(ref, count);
		rmrreq.setUnitID(1);
		rmrreq.setHeadless();
		
		trans = new ModbusSerialTransaction(myCon);
		trans.setRequest(rmrreq);
		trans.setRetries(10);
		
		trans.execute();
		rmres = (ReadMultipleRegistersResponse) trans.getResponse();

		for (int i = 0; i < rmres.getWordCount(); i++) 
		{

			MWordVaue[addr][i]=rmres.getRegisterValue(i);

		}
	}

	 public void readInputStatus(int ref, int count) throws ModbusIOException,
	 ModbusSlaveException, ModbusException, InterruptedException {
	 ridreq = new ReadInputDiscretesRequest(ref, count);
	 ridreq.setUnitID(1);
	 ridreq.setHeadless();
	
	 trans = new ModbusSerialTransaction(myCon);
	 trans.setRequest(ridreq);
	 trans.execute();
	
	 ridres = (ReadInputDiscretesResponse) trans.getResponse();
	
	
	
	 }

	public void readCoil(int ref, int count) throws ModbusIOException, ModbusSlaveException, ModbusException {
		rcreq = new ReadCoilsRequest(ref, count);
		rcreq.setUnitID(1);
		
		trans = new ModbusSerialTransaction(myCon);
		
		trans.setRequest(rcreq);
		trans.setRetries(5);
		trans.execute();
		
		rcres = (ReadCoilsResponse) trans.getResponse();
		
	}

	public void writeRegister(int ref,int value) throws ModbusIOException, ModbusSlaveException, ModbusException
	{	
		SimpleRegister myreg = new SimpleRegister(value);
		wsrreq= new WriteSingleRegisterRequest(ref, myreg);
		wsrreq.setUnitID(1);
		wsrreq.setHeadless();
		
		trans=new ModbusSerialTransaction(myCon);
		trans.setRequest(wsrreq);
		trans.execute();
		wsrres = (WriteSingleRegisterResponse) trans.getResponse();
		
		
	}

	

}
