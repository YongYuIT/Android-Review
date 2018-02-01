package com.thinking.socket;

import android.util.Log;

import org.ice4j.Transport;
import org.ice4j.TransportAddress;
import org.ice4j.ice.Agent;
import org.ice4j.ice.Component;
import org.ice4j.ice.IceMediaStream;
import org.ice4j.ice.IceProcessingState;
import org.ice4j.ice.NominationStrategy;
import org.ice4j.ice.harvest.StunCandidateHarvester;
import org.ice4j.ice.harvest.TurnCandidateHarvester;
import org.ice4j.security.LongTermCredential;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testConnToDevice() {
        boolean USE_STUN = true;
        boolean USE_TURN = false;
        int pTcpPort = 5555;
        Agent agent = new Agent();
        // STUN
        if (USE_STUN) {
            StunCandidateHarvester stunHarv = new StunCandidateHarvester(
                    new TransportAddress("sip-communicator.net",
                            3478, Transport.UDP));
            StunCandidateHarvester stun6Harv = new StunCandidateHarvester(
                    new TransportAddress("ipv6.sip-communicator.net",
                            3478, Transport.UDP));

            agent.addCandidateHarvester(stunHarv);
            agent.addCandidateHarvester(stun6Harv);
        }
        // TURN
        if (USE_TURN) {
            String[] hostnames = new String[]
                    {
                            "130.79.90.150",
                            "2001:660:4701:1001:230:5ff:fe1a:805f"
                    };
            int port = 3478;
            LongTermCredential longTermCredential = new LongTermCredential(
                    "guest", "anonymouspower!!");

            for (String hostname : hostnames) {
                agent.addCandidateHarvester(new TurnCandidateHarvester(
                        new TransportAddress(hostname, port,
                                Transport.UDP), longTermCredential));
            }
        }
        //STREAM
        IceMediaStream stream = agent.createMediaStream("data");
        long startTime = System.currentTimeMillis();
        //udp component
        try {
            agent.createComponent(stream, Transport.UDP, pTcpPort, pTcpPort, pTcpPort + 100);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        long endTime = System.currentTimeMillis();
        startTime = endTime;
        agent.setNominationStrategy(
                NominationStrategy.NOMINATE_HIGHEST_PRIO);

        agent.addStateChangeListener(new IceProcessingListener());

        //let them fight ... fights forge character.
        agent.setControlling(false);
        try {
            String localSDP = SdpUtils.createSDPDescription(agent);
            //wait a bit so that the logger can stop dumping stuff:
            Thread.sleep(500);
        } catch (Throwable e) {
            e.printStackTrace();
            return;
        }
        startTime = System.currentTimeMillis();
        try {
            SdpUtils.parseSDP(agent, "v=0\n" +
                    "o=ice4j.org 0 0 IN null null\n" +
                    "s=-\n" +
                    "t=0 0\n" +
                    "a=ice-options:trickle\n" +
                    "a=ice-ufrag:50m4a1c55qr01c\n" +
                    "a=ice-pwd:2i177t3tnaognkk6ovg55g68ci\n" +
                    "m=fuck-audio 57155 RTP/AVP 0\n" +
                    "c=IN 85.203.47.44 IP4\n" +
                    "a=mid:fuck-audio\n" +
                    "a=candidate:1 1 udp 2130706431 fe80::5054:ff:fe12:3456 5555 typ host\n" +
                    "a=candidate:2 1 udp 2130706431 fec0::5054:ff:fe12:3456 5555 typ host\n" +
                    "a=candidate:3 1 udp 2113932031 10.0.2.15 5555 typ host\n" +
                    "a=candidate:4 1 udp 1677724415 85.203.47.44 57155 typ srflx raddr 10.0.2.15 rport 5555\n");

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        agent.startConnectivityEstablishment();

        //Give processing enough time to finish. We'll System.exit() anyway
        //as soon as localAgent enters a final state.
        try {
            Thread.sleep(60000);
        } catch (Throwable e) {
            e.printStackTrace();
            return;
        }
    }

    @Test
    public void testIceAgent() {
        Agent agent = new Agent();
        String[] stun_servers = new String[]{"jitsi.org", "numb.viagenie.ca", "stun.ekiga.net", "stun.counterpath.com"};
        for (int i = 0; i < stun_servers.length; i++) {
            try {
                TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(stun_servers[i]), 3478, Transport.UDP);
                System.out.println(transportAddress.getHostAddress() + "-->" + transportAddress.getPort());
                StunCandidateHarvester candidateHarvester = new StunCandidateHarvester(transportAddress);
                agent.addCandidateHarvester(candidateHarvester);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        IceMediaStream stream = agent.createMediaStream("fuck-audio");
        int port = 5555;
        try {
            agent.createComponent(stream, Transport.UDP, port, port, port + 100);
            String dis = SdpUtils.createSDPDescription(agent);
            System.out.println(String.format("dis is \"%s\"", dis));
        } catch (Throwable e) {
            e.printStackTrace();
            return;
        }
        agent.addStateChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

            }
        });
        agent.startConnectivityEstablishment();
    }
}

class IceProcessingListener
        implements PropertyChangeListener {
    /**
     * System.exit()s as soon as ICE processing enters a final state.
     *
     * @param evt the {@link PropertyChangeEvent} containing the old and new
     *            states of ICE processing.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        long startTime = System.currentTimeMillis();

        long processingEndTime = System.currentTimeMillis();

        Object iceProcessingState = evt.getNewValue();

        System.out.println(
                "Agent entered the " + iceProcessingState + " state.");
        if (iceProcessingState == IceProcessingState.COMPLETED) {
            System.out.println(
                    "Total ICE processing time: "
                            + (processingEndTime - startTime) + "ms");
            Agent agent = (Agent) evt.getSource();
            List<IceMediaStream> streams = agent.getStreams();

            for (IceMediaStream stream : streams) {
                String streamName = stream.getName();
                System.out.println(
                        "Pairs selected for stream: " + streamName);
                List<Component> components = stream.getComponents();

                for (Component cmp : components) {
                    String cmpName = cmp.getName();
                    System.out.println(cmpName + ": "
                            + cmp.getSelectedPair());
                }
            }

            System.out.println("Printing the completed check lists:");
            for (IceMediaStream stream : streams) {
                String streamName = stream.getName();
                System.out.println("Check list for  stream: " + streamName);
                //uncomment for a more verbose output
                System.out.println(stream.getCheckList().toString());
            }

            System.out.println("Total ICE processing time to completion: "
                    + (System.currentTimeMillis() - startTime));
        } else if (iceProcessingState == IceProcessingState.TERMINATED
                || iceProcessingState == IceProcessingState.FAILED) {
                /*
                 * Though the process will be instructed to die, demonstrate
                 * that Agent instances are to be explicitly prepared for
                 * garbage collection.
                 */
            ((Agent) evt.getSource()).free();

            System.out.println("Total ICE processing time: "
                    + (System.currentTimeMillis() - startTime));
            System.exit(0);
        }
    }
}