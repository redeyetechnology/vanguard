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

package ltd.redeye.vanguard.common.config.file.messages

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Setting

@ConfigSerializable
data class PaginationMessages(
    @Setting
    var header: String = "<#64748B>==========</#64748B>  <#22D3EE><name_padded></#22D3EE>  <#BAE6FD><current_page><#64748B>/</#64748B><max_page></#BAE6FD>  <#64748B>=========",

    @Setting
    var subHeading: String = "  <#D1FAE5>Actions marked with a <#6EE7B7>★</#6EE7B7> are currently live.</#D1FAE5> ",

    @Setting
    var actionRow: String = "<active> <#BFDBFE><action></#BFDBFE> by <#BFDBFE><origin></#BFDBFE> for <#BFDBFE><short_reason></#BFDBFE>    " +
            "<click:run_command:'/userhistory detail <action>'><hover:show_text:'<#BFDBFE><player></#BFDBFE> was <#BFDBFE><action_plural></#BFDBFE> by <#BFDBFE><origin></#BFDBFE>\n\n" +
            "Reason: <#BAE6FD><full_reason></#BAE6FD>\nDuration: <#BAE6FD><duration></#BAE6FD>\nExpires: <#BAE6FD><expires></#BAE6FD>\n\n<#F5D0FE>Click for more details'><#F0ABFC>[Details]</#F0ABFC></hover></click>",

    @Setting
    var footerRow: String = "<#64748B>=========   <#F0ABFC>ᴘʀᴇᴠ</#F0ABFC>   ======   <#F0ABFC>ɴᴇxᴛ</#F0ABFC>    ==========</#64748B>",

    @Setting
    var activePlaceholder: String = "<#6EE7B7>★</#6EE7B7>",

    @Setting
    var inactivePlaceholder: String = "  "
)