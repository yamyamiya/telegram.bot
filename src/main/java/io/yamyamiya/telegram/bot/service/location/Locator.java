package io.yamyamiya.telegram.bot.service.location;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.utils.Result;

public interface Locator {
    Result<Location> locate(String string);
}
