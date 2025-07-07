package org.mystock.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MetadataGenerator {

	public <T> Map<String, String> getMetadata(T t) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", t != null ? "1" : "0");
		return metadata;
	}

	public <T> Map<String, String> getMetadata(Collection<T> collection) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount",
				String.valueOf(collection != null && !collection.isEmpty() ? collection.size() : 0));
		return metadata;
	}
}
