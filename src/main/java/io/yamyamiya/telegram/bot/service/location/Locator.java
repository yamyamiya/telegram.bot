package io.yamyamiya.telegram.bot.service.location;

import io.yamyamiya.telegram.bot.dto.Location;
import io.yamyamiya.telegram.bot.utils.Result;

/**
 * Interface providing the method locate
 * @see io.yamyamiya.telegram.bot.openAI.OpenAILocator
 */
public interface Locator {
    /**
     *  method for providing location from the received string
     * @param string the data containing city mentioning
     * @return {@link Result<Location>} (Success or Failure)
     */
    Result<Location> locate(String string);
}
