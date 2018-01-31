import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class MyUDPHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "channelActive");
		super.channelActive(ctx);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext arg0, DatagramPacket arg1) throws Exception {
		// TODO Auto-generated method stub
		Log.i("messageReceived", arg1.sender().getHostString() + "-->" + arg1.sender().getPort());
		Log.i("messageReceived", arg1.content().toString(CharsetUtil.UTF_8));
	}

	@Override
	public boolean acceptInboundMessage(Object msg) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "acceptInboundMessage");
		return super.acceptInboundMessage(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "channelRead");
		super.channelRead(arg0, arg1);
	}

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "bind");
		super.bind(ctx, localAddress, promise);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "channelWritabilityChanged");
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "close");
		super.close(ctx, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "connect");
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "disconnect");
		super.disconnect(ctx, promise);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "flush");
		super.flush(ctx);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "handlerAdded");
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "handlerRemoved");
		super.handlerRemoved(ctx);
	}

	@Override
	public boolean isSharable() {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "isSharable");
		return super.isSharable();
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "read");
		super.read(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "userEventTriggered");
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MyUDPHandler", "write");
		super.write(ctx, msg, promise);
	}

}
