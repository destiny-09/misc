package top.hiccup.jdk.io.netty.n1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler  extends ChannelHandlerAdapter {

//	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
			//do something msg
			ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");
			System.out.println("NettyServer: " + request);
			//写给客户端
			String response = "我是反馈的信息";
			ctx.writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));
			//.addListener(ChannelFutureListener.CLOSE);
			

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
