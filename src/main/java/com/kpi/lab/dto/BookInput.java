package com.kpi.lab.dto;

import java.util.Collection;
import java.util.UUID;

public record BookInput(UUID id, String name, String authorName, Collection<String> keywords) {
}
