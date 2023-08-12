package ltd.redeye.vanguard.exception

import cloud.commandframework.captions.CaptionVariable
import cloud.commandframework.captions.StandardCaptionKeys
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.ParserException
import ltd.redeye.vanguard.command.lib.type.VanguardPlayerParser

class VanguardPlayerParseException(input: String, context: CommandContext<*>) : ParserException(
    VanguardPlayerParser::class.java,
    context,
    StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_STRING,
    CaptionVariable.of("input", input)
)