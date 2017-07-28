package com.cprc.serialize.kryo;

import com.cprc.serialize.CommonSerialize;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by withqianqian@163.com on 2017/7/27.
 */
public class KryoUtils implements CommonSerialize {


    public void encode(Object msg, ByteBuf out) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Kryo kryo = new Kryo();
        //将msg序列化
        Output output = new Output(outputStream);
        kryo.writeClassAndObject(output, msg);
        output.close();

        //将字节码写到byteBuf里
        byte[] src = outputStream.toByteArray();
        out.writeInt(src.length);
        out.writeBytes(src);
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decode(ByteBuf in, List<Object> out) {
        if("EmptyByteBufBE".equals(in.toString()))
            return;
        int bodyByte = in.readInt();
        if (bodyByte < 0) {
            return;
        }
        if (in.readableBytes() < bodyByte) {
            return;
        }

        Kryo kryo = new Kryo();
        //将bytebuf写到body
        byte[] body = new byte[bodyByte];
        in.readBytes(body);

        //反序列化
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        Input input = new Input(body);
        out.add(kryo.readClassAndObject(input));

        input.close();
        try {
            byteArrayInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
