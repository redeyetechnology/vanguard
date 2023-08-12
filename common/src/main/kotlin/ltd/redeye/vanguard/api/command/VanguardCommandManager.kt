package ltd.redeye.vanguard.api.command

import cloud.commandframework.CommandManager
import cloud.commandframework.annotations.AnnotationParser
import cloud.commandframework.arguments.parser.ParserParameters
import cloud.commandframework.arguments.parser.StandardParameters
import cloud.commandframework.meta.CommandMeta
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler
import io.leangen.geantyref.TypeToken

interface PlatformCommandInitializer<S : VanguardCommandSource<S>> {

    /**
     * Creates a command manager from the platform implementation
     * @return The command manager.
     */
    fun createCommandManager(): CommandManager<S>

    /**
     * Creates an exception handler from the platform implementation. By default, all platforms use the same
     * exception handler, but this can be overridden if needed.
     * @param commandManager The command manager to apply the exception handler to.
     * @return The exception handler.
     */
    fun createExceptionHandler(commandManager: CommandManager<S>): MinecraftExceptionHandler<S> {
        return MinecraftExceptionHandler<S>().apply {
            withInvalidSenderHandler()
            withNoPermissionHandler()
            withArgumentParsingHandler()
            withCommandExecutionHandler()
            apply(commandManager) { source -> source }
        }
    }

}

class VanguardCommandManager<S : VanguardCommandSource<S>>(
    commandInitializer: PlatformCommandInitializer<S>
) {

    val commandManager: CommandManager<S>
    val annotationParser: AnnotationParser<VanguardCommandSource<S>>

    init {

        commandManager = commandInitializer.createCommandManager()
        commandInitializer.createExceptionHandler(commandManager)

        annotationParser = AnnotationParser<S>(
            commandManager,
            TypeToken.get(VanguardCommandSource::class.java)
        ) { params: ParserParameters ->
            CommandMeta.simple()
                .with(CommandMeta.DESCRIPTION, params.get(StandardParameters.DESCRIPTION, "No description provided"))
                .build()
        }


    }


}