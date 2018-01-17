package org.roof.message.api;

public interface Sender<T extends Sendable> {

	void start();

}
