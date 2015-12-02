package org.front;

public class Configuration {

    private String bindingAddress;
    private int bindingPort;

    public String getBindingAddress() {
        return bindingAddress;
    }

    public void setBindingAddress(String bindingAddress) {
        this.bindingAddress = bindingAddress;
    }

    public int getBindingPort() {
        return bindingPort;
    }

    public void setBindingPort(int bindingPort) {
        this.bindingPort = bindingPort;
    }
}
