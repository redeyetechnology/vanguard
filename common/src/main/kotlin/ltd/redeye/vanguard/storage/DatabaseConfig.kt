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

package ltd.redeye.vanguard.storage

import ltd.redeye.vanguard.storage.mongo.MongoStorageConfig
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class DatabaseConfig(
    val mongo: MongoStorageConfig = MongoStorageConfig(),

    // We'll put this at the bottom of the file, so that the driver configs are all together and take precedence over
    // the type, which is less likely to be changed.
    val driver: VanguardStorageDriver.DriverType = VanguardStorageDriver.DriverType.MONGO
)