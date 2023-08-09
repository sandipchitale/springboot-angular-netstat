package sandipchitale.springbootangular.netstat.model;

public record NetstatLine(long timestamp, Proto proto, String localAddress, int localPort, IPVersion localAddressIPVersion, String foreignAddress, int foreignPort, IPVersion foreignAddressIPVersion, State state, int pid) {
    public enum IPVersion {
        IPv4,
        IPv6,
        UNKNOWN
    }
    public enum Proto {
        TCP,
        UDP,
        UNKNOWN
    }
    public enum State {
        LISTENING,
        ESTABLISHED,
        TIME_WAIT,
        CLOSE_WAIT,
        FIN_WAIT_1,
        FIN_WAIT_2,
        SYN_SENT,
        SYN_RECEIVED,
        LAST_ACK,
        CLOSING,
        UNKNOWN
    }

    public static Proto getProto(String proto) {
        if (proto.equalsIgnoreCase("tcp")) {
            return Proto.TCP;
        } else if (proto.equalsIgnoreCase("udp")) {
            return Proto.UDP;
        } else {
            return Proto.UNKNOWN;
        }
    }

    public static IPVersion getIPVersion(String address) {
        if (address.contains(":")) {
            return IPVersion.IPv6;
        } else if (address.contains(".")) {
            return IPVersion.IPv4;
        } else {
            return IPVersion.UNKNOWN;
        }
    }

    public static String getAddress(String address) {
        return address.split(":")[0];
    }

    public static int getPort(String address) {
        return Integer.parseInt(address.split(":")[1]);
    }

    public static State getState(String state) {
        if (state.equalsIgnoreCase("LISTENING")) {
            return State.LISTENING;
        } else if (state.equalsIgnoreCase("ESTABLISHED")) {
            return State.ESTABLISHED;
        } else if (state.equalsIgnoreCase("TIME_WAIT")) {
            return State.TIME_WAIT;
        } else if (state.equalsIgnoreCase("CLOSE_WAIT")) {
            return State.CLOSE_WAIT;
        } else if (state.equalsIgnoreCase("FIN_WAIT_1")) {
            return State.FIN_WAIT_1;
        } else if (state.equalsIgnoreCase("FIN_WAIT_2")) {
            return State.FIN_WAIT_2;
        } else if (state.equalsIgnoreCase("SYN_SENT")) {
            return State.SYN_SENT;
        } else if (state.equalsIgnoreCase("SYN_RECEIVED")) {
            return State.SYN_RECEIVED;
        } else if (state.equalsIgnoreCase("LAST_ACK")) {
            return State.LAST_ACK;
        } else if (state.equalsIgnoreCase("CLOSING")) {
            return State.CLOSING;
        } else {
            return State.UNKNOWN;
        }
    }

    public NetstatLine(long timestamp, String proto, String localAddressPort, String foreignAddressPort, String state, String pid) {
        this(timestamp,
                getProto(proto),
                getAddress(localAddressPort),
                getPort(localAddressPort),
                getIPVersion(getAddress(localAddressPort)),
                getAddress(foreignAddressPort),
                getPort(foreignAddressPort),
                getIPVersion(getAddress(foreignAddressPort)),
                getState(state),
                Integer.parseInt(pid)
        );
    }

    public static NetstatLine parse(long timestamp,String line) {
        String[] tokens = line.trim().split("\\s+");
        return new NetstatLine(timestamp, tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
    }
}
