/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 EntityParent.java is part of bukkit-utilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.persistence.database.support;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class EntityParent {

	@OneToMany(targetEntity = EntityChild.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	private List<EntityChild> children;

	public List<EntityChild> getChildren() {
		return children;
	}

	public void setChildren(List<EntityChild> children) {
		this.children = children;
	}

}
