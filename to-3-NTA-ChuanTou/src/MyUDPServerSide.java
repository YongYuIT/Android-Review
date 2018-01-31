import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class MyUDPServerSide extends MyServerSide {

	public MyUDPServerSide(int _port) {
		super(_port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doJob() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(bossGroup).channel(NioDatagramChannel.class).handler(new MyUDPHandler());
			Channel ch = b.bind(port).sync().channel();
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
		}
	}

}
