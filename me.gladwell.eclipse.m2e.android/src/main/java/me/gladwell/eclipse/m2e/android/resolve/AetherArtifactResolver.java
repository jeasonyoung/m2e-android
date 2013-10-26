package me.gladwell.eclipse.m2e.android.resolve;

import javax.inject.Inject;

import me.gladwell.eclipse.m2e.android.configuration.ProjectConfigurationException;

import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;

public class AetherArtifactResolver implements ArtifactResolver {

    private final RepositorySystem repository;
    private final RepositorySystemSession session;

    @Inject
    public AetherArtifactResolver(RepositorySystem repository, RepositorySystemSession session) {
        super();
        this.repository = repository;
        this.session = session;
    }

    public Artifact resolveArtifact(RemoteRepository central, Artifact artifact) {
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(artifact);
        artifactRequest.addRepository( central );
        ArtifactResult artifactResult;
        try {
            artifactResult = repository.resolveArtifact(session, artifactRequest);
            return artifactResult.getArtifact();
        } catch (ArtifactResolutionException e) {
            throw new ProjectConfigurationException(e);
        }
    }

}
