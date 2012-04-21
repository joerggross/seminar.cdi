/*
 * Copyright by Jörg Groß.
 */
package de.jgross.cdiseminar.service;

import de.jgross.cdiseminar.domain.ActionLogEntry;
import java.util.List;

/**
 *
 * @author Jörg Groß
 */
public interface IActionLogService {
        
    public ActionLogEntry update(ActionLogEntry anActionLogEntry);
   
    public List<ActionLogEntry> getAll();
}
