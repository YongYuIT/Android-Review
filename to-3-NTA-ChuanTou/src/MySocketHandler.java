import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class MySocketHandler extends ChannelHandlerAdapter {

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "bind");
		super.bind(ctx, localAddress, promise);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "channelActive");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		Log.i("channelRead", ((String) msg));
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "channelWritabilityChanged");
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "close");
		super.close(ctx, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "connect");
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "disconnect");
		super.disconnect(ctx, promise);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		Log.i("exceptionCaught", cause.getMessage());
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "flush");
		super.flush(ctx);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "handlerAdded");
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "handlerRemoved");
		super.handlerRemoved(ctx);
	}

	@Override
	public boolean isSharable() {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "isSharable");
		return super.isSharable();
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		Log.i("MySocketHandler", "read");
		super.read(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		Log.i("userEventTriggered", evt.getClass().getName());
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		Log.i("userEventTriggered", ((String) msg));
		super.write(ctx, msg, promise);
	}

}
