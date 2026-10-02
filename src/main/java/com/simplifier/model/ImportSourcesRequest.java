package com.simplifier.model;

import java.util.List;

public record ImportSourcesRequest(String searchId, List<String> sourceIds) {
}
