package util;

import static org.junit.Assert.*;

import org.junit.Test;

import net.wimpi.modbus.Modbus;
public class Modbus4jtest{
private static ModbusRTU m4j = new ModbusRTU();

@Test
public void testreadreg() throws Exception {
	
	m4j.openCon("COM5", 9600, 8, "None", 1,Modbus.SERIAL_ENCODING_RTU,false);
	
	System.out.println("�������ӳɹ�");
	Thread.sleep(1000);
	m4j.readKeepRegisters(300,4,1);

	System.out.println(m4j.MWordVaue[1][0]);
	
	m4j.closeCon();
	
}
//@Test
public void testWrite() throws Exception
{
	m4j.openCon("COM5", 9600, 8, "None", 1,Modbus.SERIAL_ENCODING_RTU,false);
	Thread.sleep(1000);
	m4j.writeRegister(300, 10);
	m4j.closeCon();
}
//@Test
public void testCoils() throws Exception
{
	m4j.openCon("COM5", 9600, 8, "None", 1,Modbus.SERIAL_ENCODING_RTU,false);
	Thread.sleep(3000);
	System.out.println("�������ӳɹ�");
	m4j.readCoil(500, 9);

	for(int i =0;i<m4j.rcres.getBitCount();i++)
	{
	System.out.println(m4j.rcres.getCoilStatus(i));
	}
	m4j.closeCon();
}



}

