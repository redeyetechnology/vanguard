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

package ltd.redeye.vanguard.common.command.lib

import cloud.commandframework.CommandManager
import cloud.commandframework.annotations.AnnotationParser
import cloud.commandframework.arguments.parser.ParserParameters
import cloud.commandframework.arguments.parser.StandardParameters
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.meta.CommandMeta
import cloud.commandframework.meta.SimpleCommandMeta
import cloud.commandframework.minecraft.extras.MinecraftHelp
import ltd.redeye.vanguard.common.command.CommandRegistry
import ltd.redeye.vanguard.common.command.lib.types.PlatformCommandInitializer
import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource
import net.kyori.adventure.text.format.TextColor

/**
 * The Command Manager for Vanguard.
 * @param commandInitializer The command initializer for the platform. This is registered in the platform module
 */
class VanguardCommandManager(
    commandInitializer: PlatformCommandInitializer
) {
    private val commandManager: CommandManager<VanguardCommandSource<*>>
    private val annotationParser: AnnotationParser<VanguardCommandSource<*>>

    private fun buildAnnotationParser(commandManager: CommandManager<VanguardCommandSource<*>>): AnnotationParser<VanguardCommandSource<*>> {
        val commandMetaFunction: (ParserParameters) -> SimpleCommandMeta = { params: ParserParameters ->
            CommandMeta.simple()
                .with(CommandMeta.DESCRIPTION, params.get(StandardParameters.DESCRIPTION, "No description provided"))
                .build()
        }

        return AnnotationParser(
            commandManager,
            VanguardCommandSource::class.java,
            commandMetaFunction
        )
    }

    private fun defineHelpCommand() {
        val help = MinecraftHelp(
            "/vanguard help",
            { source -> source },
            this.commandManager
        )

        help.helpColors = MinecraftHelp.HelpColors.of(
            TextColor.color(203, 213, 225), // Primary
            TextColor.color(9, 147, 232), // Highlight
            TextColor.color(86, 198, 232), //alternateHighlight
            TextColor.color(142, 195, 207),
            TextColor.color(73, 252, 255) // accent
        )

        // Set commands per page
        help.setMaxResultsPerPage(8)

        // Define the '/vanguard help' command.
        commandManager.command(
            commandManager.commandBuilder("vanguard")
                .literal("help")
                .argument(StringArgument.optional("query", StringArgument.StringMode.GREEDY))
                .handler { context -> help.queryCommands(context.getOrDefault("query", "")!!, context.sender) }
                .build()
        )
    }

    fun registerCommands(vararg command: VanguardCommand) {
        command.forEach { cmd ->
            annotationParser.parse(cmd)
        }
    }

    init {
        commandManager = commandInitializer.createCommandManager()
        commandInitializer.createExceptionHandler(commandManager)
        annotationParser = buildAnnotationParser(commandManager)

        defineHelpCommand()
        CommandRegistry.registerCommands(this)
    }
}