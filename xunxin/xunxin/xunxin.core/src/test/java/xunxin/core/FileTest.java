package xunxin.core;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class FileTest {

	public static void main(String[] args) {
		Charset charset = Charset.forName("GBK");
		CharsetDecoder decoder = charset.newDecoder();

		try {
			ByteBuffer bytebf = ByteBuffer.allocate(1024);
			CharBuffer charbf = CharBuffer.allocate(1024);

			// TODO Auto-generated method stub
			File file = new File("D:\\nio.txt");			
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			FileChannel channel = raf.getChannel();

			int i = channel.read(bytebf);

			while (i != -1) {
				bytebf.flip();
				decoder.decode(bytebf, charbf, false);
				charbf.flip();
				while (charbf.hasRemaining()) {
					System.out.print(charbf.get());
				}
				bytebf.clear();
				i = channel.read(bytebf);
			}

			channel.close();
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
