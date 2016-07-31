package dev.etna.jabberclient.interfaces;

import dev.etna.jabberclient.tasks.Task;

public interface ITaskObservable
{
    void onAsyncTaskComplete(Task task);
}
