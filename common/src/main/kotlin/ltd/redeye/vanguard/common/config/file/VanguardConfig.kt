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

package ltd.redeye.vanguard.common.config.file

import ltd.redeye.vanguard.common.network.NetworkConfig
import ltd.redeye.vanguard.common.storage.DatabaseConfig
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class VanguardConfig(
    @Comment("This is the prefix used for any admin commands. If you set it to blank, then nothing will be shown.")
    val prefix: String = "<#22D3EE><bold>Vanguard</bold></#22D3EE> <#64748B>»</#64748B> <#D6D3D1>",

    @Comment("You must have a database driver to use Vanguard. The default is MongoDB, but you can use MySQL or flatfile.")
    val database: DatabaseConfig = DatabaseConfig(),

    @Comment("For cross-server communication, you can configure Redis for instantaneous and reliable communication.")
    val network: NetworkConfig = NetworkConfig(),

    @Comment("The date format used for punishments.")
    val dateFormat: String = "dd/MM/yyyy HH:mm:ss",
)