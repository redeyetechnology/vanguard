/*
 * Vanguard
 * Copyright (C) 2023 RedEye Technologies Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ltd.redeye.vanguard.common.exception

import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.captions.StandardCaptionKeys
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.ParserException
import ltd.redeye.vanguard.common.command.lib.VanguardPlayerParser

class VanguardPlayerParseException(input: String, context: CommandContext<*>) : ParserException(
    VanguardPlayerParser::class.java,
    context,
    StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_STRING,
    CaptionVariable.of("input", input),
    CaptionVariable.of("stringMode", "player, uuid or IP address")
)